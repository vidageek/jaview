package net.vidageek.jaview.plugin

import org.specs2.mutable.Specification
import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner

@RunWith(classOf[JUnitRunner])
class ValPluginConfigSpec extends Specification {

  "val plugin" should {
    "produce the correct string that declares the val" in {
      new ValPluginFixture().line ===
        "val render = new net.vidageek.jaview.plugin.P(cache);"
    }
  }

}

class ValPluginFixture extends ValPluginConfig {
  def valName = "render"
  def constructorCall = "(cache)"
  def pluginClass = classOf[P]
}

class P