package jaview

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification

@RunWith(classOf[JUnitRunner])
class ClassPathLocationSpec extends Specification {

  "classpath location" should {
    "find view in classpath" in {
      val view = new ClassPathLocation().read("/asdf.jaview")
      view === Some("view-type ()\n<html>")
    }

    "return none if no view is found" in {
      val view = new ClassPathLocation().read("/asdrubal.jaview")
      view === None
    }
  }

}