package jaview.render

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.specs2.mutable.Specification
import jaview.CachedJaview
import jaview.Jaview
import jaview.CachedJaview

@RunWith(classOf[JUnitRunner])
class RenderPluginSpec extends Specification {

  "render" should {
    "include sub view" in {
      val jaview = new CachedJaview()("/asdf")("asdf")
      jaview === "<html> <h1>asdf</h1></html>"
    }
  }
}