package net.vidageek.jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import net.vidageek.jaview.compiler.JaviewSyntax

@RunWith(classOf[JUnitRunner])
class JaviewSpec extends Specification {

  "jaview" should {
    "compile simple view" in {
      val value = new Jaview("", "view-type ()\n<html>asdf</html>", null)()
      value must_== "<html>asdf</html>"
    }

    "compile simple view with self closing tags" in {
      val value = new Jaview("", "view-type ()\n<link />", null)()
      value must_== "<link />"
    }

    "compile simple view with tag attributes" in {
      val value = new Jaview("",
        "view-type (asdf: String)\n<html lang=\"pt_BR\" disabled class=\"@asdf\">asdf</html>", null)("foo")
      value must_== "<html lang=\"pt_BR\" disabled class=\"foo\">asdf</html>"
    }

    "compile simple view with unused parameters" in {
      val value = new Jaview("", "view-type (a : Int)\n<html>asdf</html>", null)(1)
      value must_== "<html>asdf</html>"
    }

    "compile simple view with single parameter and replace its value" in {
      val value = new Jaview("", "view-type (a : Int)\n<html>asdf@a</html>", null)(1)
      value must_== "<html>asdf1</html>"
    }

    "compile simple view with single parameter and replace its value when invoking method" in {
      val value = new Jaview("", "view-type (a:Option[Int])\n<html>asdf@a.get</html>", null)(Some(1))
      value must_== "<html>asdf1</html>"
    }

    "compile simple view with single parameter and replace its value when invoking method with parameters" in {
      val value = new Jaview("", "view-type (a:Option[Int])\n<html>asdf@a.getOrElse(\"bla\")</html>", null)(None)
      value must_== "<html>asdfbla</html>"
    }

    "compile view with single list parameter and call it's foldLeft method" in {
      val value = new Jaview("",
        "view-type (a : List[Int])\n<ul>@a -> item {<li>@item</li>}</ul>", null)(List(1, 2))
      value must_== "<ul><li>1</li><li>2</li></ul>"
    }

    "compile view with single list parameter and call it's Some foldLeft method" in {
      val value = new Jaview("", "view-type (a : Option[Int])\n@a -> item {@item}", null)(Option(1))
      value must_== "1"
    }

    "compile view with single list parameter and call it's None foldLeft method" in {
      val value = new Jaview("", "view-type (a : Option[Int])\n@a -> item {@item}", null)(None)
      value must_== ""
    }

    "compile view with arbitrary scala expression and output it's value" in {
      val value = new Jaview("", "view-type ()\n@{List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}}", null)()
      value must_== "123"
    }

    "compile view with arbitrary scala expression with nested blocks and output it's value" in {
      val value = new Jaview("", "view-type ()\n@{{{123}}+{123}}", null)()
      value must_== "246"
    }

    "compile view with arbitrary scala expression and not output if unit is returned" in {
      val value = new Jaview("", "view-type ()\n@{val a=12;}", null)()
      value must_== ""
    }

    "compile view with raw string" in {
      val value = new Jaview("", "view-type (item:String)\n@raw { @item }", null)("asdf")
      value must_== " asdf "
    }

    "compile view with escaped reserved chars" in {
      val value = new Jaview("", s"""view-type ()
${JaviewSyntax.reservedChars.map("`" + _).mkString(" ")}""", null)()
      value must_== s"""${JaviewSyntax.reservedChars.mkString(" ")}"""
    }

    "compile view with escaped } inside raw string" in {
      val value = new Jaview("", s"view-type ()\n@raw { `} }", null)()
      value must_== " } "
    }

    "compile view and preserve spaces" in {
      val value = new Jaview("", """view-type (title:String, l:List[String])
<html>
	<head>
		<title>@title</title>
	</head>
	<body>
		@{ "asdf" }
		<ul>
		@l -> item {
			<li>@item</li>
		}
		</ul>
	</body>
</html>
""", null)("title", List("abc", "cde"))
      value must_== """<html>
	<head>
		<title>title</title>
	</head>
	<body>
		asdf
		<ul>
		
			<li>abc</li>
		
			<li>cde</li>
		
		</ul>
	</body>
</html>
"""
    }
  }
}