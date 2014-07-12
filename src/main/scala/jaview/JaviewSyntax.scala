package jaview

import scala.util.parsing.combinator.RegexParsers
import scala.util._

class JaviewSyntax extends RegexParsers {

  override def skipWhitespace = false

  val reservedChars = "<>@{}"

  def root : Parser[Root] = "view-type " ~> parameterList ~ "\n" ~ rep(expression) ^^ {
    case p ~ _ ~ list => Root(p, list : _*)
  }

  def expression : Parser[Expression] = tag | interpolation | text

  def interpolation = arbitraryCode | variable ||| fold

  def fold = "@" ~> "[\\w.]+".r ~ " -> " ~ "\\w+".r ~ " {" ~ rep(expression) <~ "}" ^^ {
    case variable ~ _ ~ varName ~ _ ~ content =>
      Fold(variable, varName, content : _*)
  }

  def variable = "@" ~> "[\\w.]+".r ^^ Variable

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

  def tag = "<" ~> "[\\w/]+".r <~ ">" ^^ Tag

  def parameterList = """\([^)]*\)""".r

  def apply(input : String) : Root = parseAll(root, input) match {
    case Success(jaview, _) => jaview
    case f => throw new RuntimeException(s"$f")
  }

  def test = {
    println(parseAll(root, "view-type ()\n<ul>@abc -> item {<li>@item</li>}</ul>"))
  }
}

object A extends App {
  new JaviewSyntax().test
}