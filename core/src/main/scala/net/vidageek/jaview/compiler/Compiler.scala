package net.vidageek.jaview.compiler

import java.io.File
import java.io.PrintWriter

class Compiler {

  def toClass(view: String) = {
    val root = new JaviewSyntax(None)(view)
    new Compile().apply(root.scalaCode)
  }

  def toFile(viewName: String, view: String, dstFolder: File) = {
    val name = classNameFor(viewName)
    val root = new JaviewSyntax(Option(name)).apply(view);
    val writer = new PrintWriter(new File(dstFolder, s"${classNameFor(viewName)}.scala"))
    writer.write(root.scalaCode)
    writer.close();
  }

  def classNameFor(viewName: String) =
    s"jaview_precompiled_View_${viewName.replace("/", "_")}"

}

class Compile() {
  import scala.reflect.runtime._
  import scala.tools.reflect.ToolBox

  val cm = universe.runtimeMirror(getClass.getClassLoader)
  val tb = cm.mkToolBox()

  def apply(code: String) = {
    tb.eval(tb.parse(code)).asInstanceOf[Class[_]]
  }
}