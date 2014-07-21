package net.vidageek.jaview

import scala.io.Source
import java.util.Enumeration
import java.net.URL
import scala.collection.mutable.ListBuffer
import net.vidageek.jaview.plugin.ValPluginConfig
import scala.reflect.runtime.universe._
import scala.reflect.ClassTag

object JaviewConfig {

  private val plugins = getClass().
    getClassLoader().
    getResources("jaview.plugin").
    toList.
    flatMap(Source.fromURL(_).getLines()).
    map(Class.forName).
    map(_.newInstance())

  def valPlugins = only[ValPluginConfig]

  def only[T : ClassTag] : List[T] = plugins.filter {
    case _ : T => true
    case _ => false
  }.map(_.asInstanceOf[T])

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