package jaview

import scala.io.Source
import java.util.concurrent.ConcurrentHashMap
import scala.collection.JavaConverters._

class CachedJaview(locations : List[ViewLocation] = DefaultLocations()) {

  val cache = new ConcurrentHashMap[String, Jaview]().asScala

  def apply(viewName : String) : Jaview = {
    cache.getOrElseUpdate(viewName, {
      locations.
        map(_.read(s"$viewName.jaview")).
        find(_.isDefined).
        flatten.
        map(new Jaview(_)).
        getOrElse(throw new Exception("sera resolvido"))
    })
  }
}

object DefaultLocations {
  def apply() =
    List(new ClassPathLocation())
}

trait ViewLocation {
  def read(viewName : String) : Option[String]
}

class ClassPathLocation extends ViewLocation {
  def read(viewName : String) : Option[String] =
    Option(this.getClass.getResourceAsStream(viewName)).
      map(Source.fromInputStream(_)).
      map(_.getLines().mkString("\n"))
}