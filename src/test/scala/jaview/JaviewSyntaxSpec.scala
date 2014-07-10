package jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JaviewSyntaxSpec extends Specification {

  "jaview syntax" should {
    "parse view-type with no parameters" in {
      val jaview = new JaviewSyntax()("view-type ()")
      jaview must_== Root("()")
    }

    "parse view-type with single simple parameter" in {
      val jaview = new JaviewSyntax()("view-type (a: String)")
      jaview must_== Root("(a: String)")
    }

    "parse html tags" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>")
      jaview must_== Root("()", Tag("html"))
    }

    "parse html tags with text" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html> a\nasd")
      jaview must_== Root("()", Tag("html"), Text("a\nasd"))
    }

    "parse multiple html tags with text" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html> a\nasd<a>")
      jaview must_== Root("()", Tag("html"), Text("a\nasd"), Tag("a"))
    }

    "parse view-type with no parameters and remove leading \n" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>")
      jaview must_== Root("()", Tag("html"))
    }

    "parse variable interpolation" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>@abc")
      jaview must_== Root("()", Tag("html"), Variable("abc"))
    }

    "parse nested variable interpolation" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>@abc.cde")
      jaview must_== Root("()", Tag("html"), Variable("abc.cde"))
    }

    "parse map interpolation" in {
      val jaview = new JaviewSyntax()("view-type ()\n<ul>@abc -> item { <li>@item</li> } </ul>")
      jaview must_== Root("()", Tag("ul"),
        Fold("abc", "item", Tag("li"), Variable("item"), Tag("/li")), Tag("/ul"))
    }
  }

}