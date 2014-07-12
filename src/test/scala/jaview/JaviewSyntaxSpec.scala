package jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JaviewSyntaxSpec extends Specification {

  "jaview syntax" should {

    "not skip white space" in {
      new JaviewSyntax().skipWhitespace must beFalse
    }

    "parse view-type with no parameters" in {
      val jaview = new JaviewSyntax()("view-type ()\n")
      jaview must_== Root("()")
    }

    "parse view-type with single simple parameter" in {
      val jaview = new JaviewSyntax()("view-type (a: String)\n")
      jaview must_== Root("(a: String)")
    }

    "parse html tags" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>")
      jaview must_== Root("()", Tag("html"))
    }

    "parse html tags with attributes" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html disabled lang=\"pt_BR\" class=\"@asdf\" data-x=\"asdf @value xpto\">")
      jaview must_== Root("()", Tag("html",
        Attribute("disabled"),
        Attribute("lang", Text("pt_BR")),
        Attribute("class", Variable("asdf")),
        Attribute("data-x", Text("asdf "), Variable("value"), Text(" xpto"))))
    }

    "parse html tags with text" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>a\nasd")
      jaview must_== Root("()", Tag("html"), Text("a\nasd"))
    }

    "parse multiple html tags with text" in {
      val jaview = new JaviewSyntax()("view-type ()\n<html>a\nasd<a>")
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

    "parse fold interpolation" in {
      val jaview = new JaviewSyntax()("view-type ()\n<ul>@abc -> item {<li>@item</li>}</ul>")
      jaview must_== Root("()", Tag("ul"),
        Fold("abc", "item", Tag("li"), Variable("item"), Tag("/li")), Tag("/ul"))
    }

    "parse arbitrary code block with nested block" in {
      val jaview = new JaviewSyntax()("view-type ()\n@{List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}}")
      jaview must_== Root("()", Code("List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}"))
    }

    "parse repeated arbitrary code block" in {
      val jaview = new JaviewSyntax()("view-type ()\n@{{123}+{123}}")
      jaview must_== Root("()", Code("{123}+{123}"))
    }
  }

}