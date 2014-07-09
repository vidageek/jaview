package jaview

import scala.util.parsing.combinator.RegexParsers
import scala.util._

class JaviewSyntax extends RegexParsers {

  def jaview : Parser[Root] = "view-type " ~> parameterList ~ rep(node) ^^ {
    case p ~ list => Root(p, list : _*)
  }

  def node = (tag | text)

  def text = "[^<]+".r ^^ { Text(_) }

  def tag = "<" ~> "[^>]+".r <~ ">" ^^ { Tag(_) }

  def parameterList = """\([^)]*\)""".r

  def apply(input : String) = parseAll(jaview, input) match {
    case Success(jaview, _) => jaview
    case Failure(a, b) => throw new RuntimeException(s"$a $b")
    case _ => throw new RuntimeException()
  }
}
