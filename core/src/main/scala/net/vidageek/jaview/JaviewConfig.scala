package net.vidageek.jaview

import scala.io.Source
import java.util.Enumeration
import java.net.URL
import scala.collection.mutable.ListBuffer

object JaviewConfig {

  private val plugins = getClass().
    getClassLoader().
    getResources("jaview.plugin").
    toList.
    flatMap(Source.fromURL(_).getLines())

  def imports = plugins.filter(_.startsWith("import "))

  def pluginStart = plugins.filterNot(_.startsWith("import "))

  private implicit class AddToList(val enum : Enumeration[URL]) extends AnyVal {
    def toList : List[URL] = {
      val list = new ListBuffer[URL]
      while (enum.hasMoreElements()) {
        list += (enum.nextElement())
      }
      list.toList
    }
  }
}