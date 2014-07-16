package jaview

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class CachedJaviewSpec extends Specification {

  "cached jaview" should {

    "return same jaview for an id" in {
      val cached = new CachedJaview()
      cached("/asdf") must be(cached("/asdf"))
      cached("/asdf") must be(cached("/asdf"))
    }

    "find jaview in path" in {
      val cached = new CachedJaview()
      cached("/asdf") must not(beNull)
    }
  }

}