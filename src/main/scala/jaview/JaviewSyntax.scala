package jaview

import scala.util.parsing.combinator.RegexParsers
import scala.util._

class JaviewSyntax extends RegexParsers {

  def jaview : Parser[Root] = "view-type " ~> parameterList ~ rep(node) ^^ {
    case p ~ list => Root(p, list : _*)
  }

  def node : Parser[Expression] = (tag | interpolation | text)

  def interpolation = arbitraryCode | "@" ~ "[\\w.]+".r ~ opt("->" ~ "\\w+".r ~ "{" ~ rep(node) ~ "}") ^^ {
    case at ~ variable ~ Some(arrow ~ varName ~ obracket ~ content ~ cbracket) =>
      Fold(variable, varName, content : _*)
    case at ~ variable ~ None => Variable(variable)
  }

  def arbitraryCode = "@{" ~> "[^{}]*".r ~ opt(block) ~ "[^}]*".r <~ "}" ^^ {
    case before ~ Some(block) ~ after => Code(s"$before $block $after")
    case before ~ None ~ after => Code(s"$before $after")
  }

  def block : Parser[String] = rep("{" ~> "[^{}]*".r ~ opt(block) ~ "[^}]*".r ~ "}" ~ opt("[^{}]*".r)) ^^ {
    _.map {
      case before ~ Some(block) ~ after ~ _ ~ Some(aafter) => s"{ $before $block $after } $aafter"
      case before ~ Some(block) ~ after ~ _ ~ None => s"{ $before $block $after}"
      case before ~ None ~ after ~ _ ~ Some(aafter) => s"{ $before $after } $aafter"
      case before ~ None ~ after ~ _ ~ None => s"{ $before $after }"
    }.mkString("")

  }

  def text = "[^<>@{}]+".r ^^ { Text(_) }

  def tag = "<" ~> "[^>]+".r <~ ">" ^^ { Tag }

  def parameterList = """\([^)]*\)""".r

  def apply(input : String) : Root = parseAll(jaview, input) match {
    case Success(jaview, _) => jaview
    case Failure(a, b) => throw new RuntimeException(s"$a $b")
    case _ => throw new RuntimeException()
  }

  def test = {
    println(parseAll(block, "{asd{asdasd}mkmk}"))
  }
}

object A extends App {
  new JaviewSyntax().test
}