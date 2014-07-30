package net.vidageek.jaview.compiler

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JaviewSyntaxSpec extends Specification {

  "jaview syntax" should {

    "not skip white space" in {
      new JaviewSyntax(None).skipWhitespace must beFalse
    }

    "parse view-type with no parameters" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n")
      jaview must_== Root(None, "()")
    }

    "parse view-type with single simple parameter" in {
      val jaview = new JaviewSyntax(None)("view-type (a: String)\n")
      jaview must_== Root(None, "(a: String)")
    }

    "parse doctype" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<!DOCTYPE html>")
      jaview must_== Root(None, "()", Tag("!DOCTYPE", false, Attribute("html")))
    }

    "parse html tags" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>")
      jaview must_== Root(None, "()", Tag("html", false))
    }

    "parse self closing tag" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<link />")
      jaview must_== Root(None, "()", Tag("link", true, Text(" ")))
    }

    "parse html tags with attributes" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html disabled lang=\"pt_BR\" class=\"@asdf\" data-x=\"asdf @value xpto\">")
      jaview must_== Root(None, "()", Tag("html", false,
        Attribute("disabled"),
        Attribute("lang", Text("pt_BR")),
        Attribute("class", CodeSnippet("asdf")),
        Attribute("data-x", Text("asdf "), CodeSnippet("value"), Text(" xpto"))))
    }

    "parse html tags with text" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>a\nasd")
      jaview must_== Root(None, "()", Tag("html", false), Text("a\nasd"))
    }

    "parse multiple html tags with text" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>a\nasd<a>")
      jaview must_== Root(None, "()", Tag("html", false), Text("a\nasd"), Tag("a", false))
    }

    "parse view-type with no parameters and remove leading \\n" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>")
      jaview must_== Root(None, "()", Tag("html", false))
    }

    "parse variable interpolation" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>@abc")
      jaview must_== Root(None, "()", Tag("html", false), CodeSnippet("abc"))
    }

    "parse nested variable interpolation" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html>@abc.cde")
      jaview must_== Root(None, "()", Tag("html", false), CodeSnippet("abc.cde"))
    }

    "parse variable interpolation at attribute level" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html @abc.cde>")
      jaview must_== Root(None, "()", Tag("html", false, Text(" "), CodeSnippet("abc.cde")))
    }

    "parse method invocation" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html> @render(\"/bla\")(1)</html>")
      jaview must_== Root(None, "()", Tag("html", false), Text(" "), CodeSnippet("render(\"/bla\")(1)"), Tag("/html", false))
    }

    "parse method invocation with empty parameters" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html> @render(\"/bla\")()</html>")
      jaview must_== Root(None, "()", Tag("html", false), Text(" "), CodeSnippet("render(\"/bla\")()"), Tag("/html", false))
    }

    "parse method invocation with multiple parameters" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<html> @render(\"/bla\")(1, 2)</html>")
      jaview must_== Root(None, "()", Tag("html", false), Text(" "), CodeSnippet("render(\"/bla\")(1, 2)"), Tag("/html", false))
    }

    "parse fold interpolation" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n<ul>@abc -> item {<li>@item</li>}</ul>")
      jaview must_== Root(None, "()", Tag("ul", false),
        Fold("abc", "item", Tag("li", false), CodeSnippet("item"), Tag("/li", false)), Tag("/ul", false))
    }

    "parse arbitrary code block with nested block" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n@{List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}}")
      jaview must_== Root(None, "()", Code("List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}"))
    }

    "parse repeated arbitrary code block" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n@{{123}+{123}}")
      jaview must_== Root(None, "()", Code("{123}+{123}"))
    }

    "parse raw block" in {
      val jaview = new JaviewSyntax(None)("view-type ()\n@raw { @item }")
      jaview must_== Root(None, "()", Raw(Text(" "), CodeSnippet("item"), Text(" ")))
    }

    "parse view with escaped reserved chars" in {
      val jaview = new JaviewSyntax(None)(s"""view-type ()
${JaviewSyntax.reservedChars.map("`" + _).mkString(" ")}""")

      val chars = JaviewSyntax.reservedChars.toList.
        flatMap(c => List(EscapedChar("" + c), Text(" "))).
        dropRight(1)

      jaview must_== Root(None, "()", chars: _*)
    }

    "parse view with escaped } inside raw string" in {
      val value = new JaviewSyntax(None)(s"view-type ()\n@raw { `} }")
      value must_== Root(None, "()", Raw(Text(" "), EscapedChar("}"), Text(" ")))
    }

  }

}