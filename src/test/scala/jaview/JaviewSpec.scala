package jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class JaviewSpec extends Specification {

  "jaview" should {
    "compile simple view" in {
      val value = new Jaview("view-type ()\n<html>asdf</html>")()
      value must_== "<html>asdf</html>"
    }

    "compile simple view with tag attributes" in {
      val value = new Jaview(
        "view-type (asdf: String)\n<html lang=\"pt_BR\" disabled class=\"@asdf\">asdf</html>")("foo")
      value must_== "<html lang=\"pt_BR\" disabled class=\"foo\">asdf</html>"
    }

    "compile simple view with unused parameters" in {
      val value = new Jaview("view-type (a : Int)\n<html>asdf</html>")(1)
      value must_== "<html>asdf</html>"
    }

    "compile simple view with single parameter and replace its value" in {
      val value = new Jaview("view-type (a : Int)\n<html>asdf@a</html>")(1)
      value must_== "<html>asdf1</html>"
    }

    "compile simple view with single parameter and replace its value when invoking method" in {
      val value = new Jaview("view-type (a:Option[Int])\n<html>asdf@a.get</html>")(Some(1))
      value must_== "<html>asdf1</html>"
    }

    "compile simple view with single parameter and replace its value when invoking method with parameters" in {
      val value = new Jaview("view-type (a:Option[Int])\n<html>asdf@a.getOrElse(\"bla\")</html>")(None)
      value must_== "<html>asdfbla</html>"
    }

    "compile view with single list parameter and call it's foldLeft method" in {
      val value = new Jaview(
        "view-type (a : List[Int])\n<ul>@a -> item {<li>@item</li>}</ul>")(List(1, 2))
      value must_== "<ul><li>1</li><li>2</li></ul>"
    }

    "compile view with single list parameter and call it's Some foldLeft method" in {
      val value = new Jaview("view-type (a : Option[Int])\n@a -> item {@item}")(Option(1))
      value must_== "1"
    }

    "compile view with single list parameter and call it's None foldLeft method" in {
      val value = new Jaview("view-type (a : Option[Int])\n@a -> item {@item}")(None)
      value must_== ""
    }

    "compile view with arbitrary scala expression and output it's value" in {
      val value = new Jaview("view-type ()\n@{List(1, 2, 3).foldLeft(\"\"){(a,b)=>a+b}}")()
      value must_== "123"
    }

    "compile view with arbitrary scala expression with nested blocks and output it's value" in {
      val value = new Jaview("view-type ()\n@{{{123}}+{123}}")()
      value must_== "246"
    }

    "compile view with arbitrary scala expression and not output if unit is returned" in {
      val value = new Jaview("view-type ()\n@{val a=12;}")()
      value must_== ""
    }

    "compile view with raw string" in {
      val value = new Jaview("view-type (item:String)\n@raw { @item }")("asdf")
      value must_== " asdf "
    }

    "compile view with escaped reserved chars" in {
      val value = new Jaview(s"""view-type ()
${JaviewSyntax.reservedChars.map("`" + _).mkString(" ")}""")()
      value must_== s"""${JaviewSyntax.reservedChars.mkString(" ")}"""
    }

    "compile view with escaped } inside raw string" in {
      val value = new Jaview(s"view-type ()\n@raw { `} }")()
      value must_== " } "
    }

    "compile view and preserve spaces" in {
      val value = new Jaview("""view-type (title:String, l:List[String])
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
""")("title", List("abc", "cde"))
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