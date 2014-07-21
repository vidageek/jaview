package net.vidageek.jaview.plugin

trait ValPluginConfig {
  def valName : String
  def constructorCall : String
  def pluginClass : Class[_]

  def line = s"val $valName = new ${pluginClass.getName}$constructorCall;"

}