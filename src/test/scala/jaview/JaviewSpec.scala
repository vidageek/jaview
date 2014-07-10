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

    "compile simple view with unused parameters" in {
      val value = new Jaview("view-type (a : Int)\n<html>asdf</html>")(1)
      value must_== "<html>asdf</html>"
    }

    "compile simple view with single parameter and replace its value" in {
      val value = new Jaview("view-type (a : Int)\n<html>asdf @a</html>")(1)
      value must_== "<html>asdf 1</html>"
    }
  }

}