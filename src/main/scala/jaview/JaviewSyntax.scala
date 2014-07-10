package jaview

import scala.util.parsing.combinator.RegexParsers
import scala.util._

class JaviewSyntax extends RegexParsers {

  def jaview : Parser[Root] = "view-type " ~> parameterList ~ rep(node) ^^ {
    case p ~ list => Root(p, list : _*)
  }

  def node : Parser[Expression] = (tag | interpolation | text)

  def interpolation = "@" ~ "\\w+".r ~ opt("->" ~ "\\w+".r ~ "{" ~ rep(node) ~ "}") ^^ {
    case at ~ variable ~ Some(arrow ~ varName ~ obracket ~ content ~ cbracket) =>
      ApplyMap(variable, varName, content : _*)
    case at ~ variable ~ None => Variable(variable)
  }

  def text = "[^<>@{}]+".r ^^ { Text(_) }

  def tag = "<" ~> "[^>]+".r <~ ">" ^^ { Tag }

  def parameterList = """\([^)]*\)""".r

  def apply(input : String) : Root = parseAll(jaview, input) match {
    case Success(jaview, _) => jaview
    case Failure(a, b) => throw new RuntimeException(s"$a $b")
    case _ => throw new RuntimeException()
  }
}