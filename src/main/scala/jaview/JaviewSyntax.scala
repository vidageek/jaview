package jaview

import scala.util.parsing.combinator.RegexParsers
import scala.util._

object JaviewSyntax {

  val reservedChars = "`<>@{}"
}

class JaviewSyntax extends RegexParsers {

  import JaviewSyntax._

  override def skipWhitespace = false

  def root : Parser[Root] = "view-type " ~> parameterList ~ "\n" ~ rep(expression) ^^ {
    case p ~ _ ~ list => Root(p, list : _*)
  }

  def expression : Parser[Expression] = escapedChar | raw | tag | interpolation | text

  def escapedChar = "`" ~> s"[$reservedChars]".r ^^ EscapedChar

  def raw = "@raw" ~ "\\s*".r ~ "{" ~> rep(expression) <~ "}" ^^ {
    case list => Raw(list : _*)
  }

  def interpolation = arbitraryCode | codeSnippet ||| fold

  def fold = "@" ~> "[\\w.]+".r ~ " -> " ~ "\\w+".r ~ " {" ~ rep(expression) <~ "}" ^^ {
    case variable ~ _ ~ varName ~ _ ~ content =>
      Fold(variable, varName, content : _*)
  }

  def codeSnippet = "@" ~> "[\\w.]+".r ~ rep(parentesesBlock) ^^ {
    case name ~ parameterLists => CodeSnippet(s"$name${parameterLists.map("(" + _ + ")").mkString("")}")
  }

  def parentesesBlock = "(" ~> "[\\w.\"]+".r <~ ")"

  def arbitraryCode = "@{" ~> "[^{}]*".r ~ opt(block) ~ "[^}]*".r <~ "}" ^^ {
    case before ~ Some(block) ~ after => Code(s"$before$block$after")
    case before ~ None ~ after => Code(s"$before$after")
  }

  def block : Parser[String] = rep("{" ~> "[^{}]*".r ~ opt(block) ~ "[^}]*".r ~ "}" ~ opt("[^{}]*".r)) ^^ {
    _.map {
      case before ~ Some(block) ~ after ~ _ ~ Some(aafter) => s"{$before$block$after}$aafter"
      case before ~ Some(block) ~ after ~ _ ~ None => s"{$before$block$after}"
      case before ~ None ~ after ~ _ ~ Some(aafter) => s"{$before$after}$aafter"
      case before ~ None ~ after ~ _ ~ None => s"{$before$after}"
    }.mkString("")

  }

  def text = s"[^$reservedChars]+".r ^^ Text

  def tag = "<" ~> "[\\w/]+".r ~ rep(attribute) <~ opt("\\s+") ~ ">" ^^ {
    case name ~ list => Tag(name, list : _*)
  }

  def attribute = "\\s+".r ~> "[\\w-]+".r ~ opt("=\"" ~> rep(interpolation | attributeValueText) <~ "\"") ^^ {
    case attr ~ value => Attribute(attr, value.getOrElse(List[Expression]()) : _*)
  }

  def attributeValueText = ("[^\"" + reservedChars + "]+").r ^^ Text

  def parameterList = """\([^)]*\)""".r

  def apply(input : String) : Root = parseAll(root, input) match {
    case Success(jaview, _) => jaview
    case f => throw new RuntimeException(s"$f")
  }

  def test = {
    println(parseAll(raw, "@raw { @item }"))
  }
}

object TestParsing extends App {
  new JaviewSyntax().test
}