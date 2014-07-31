package net.vidageek.jaview.sbt

import java.io.File
import scala.io.Source

object JaviewPreCompiler {

  type Compiler = {
    def toFile(viewName: String, view: String, dstFolder: File): Unit
    def classNameFor(viewName: String): String
  }

  def compileAll(src: Seq[File], dst: File, loader: ClassLoader): Seq[File] = {

    dst.mkdirs()

    val folders = src.map(_.getAbsolutePath())

    val javiewSources = src.flatMap(listAllFiles).
      filter(_.getAbsolutePath().endsWith(".jaview")).
      filter(_.isFile()).
      map { file =>
        val fileName = folders.foldLeft(file.getAbsolutePath())(_.replace(_, ""))
        (fileName.replace(".jaview", ""), Source.fromFile(file).getLines().mkString("\n"))
      }

    val compiler = loader.loadClass("net.vidageek.jaview.compiler.Compiler").newInstance().asInstanceOf[Compiler]

    javiewSources.foreach {
      case (viewName, view) => compiler.toFile(viewName, view, dst)
    }

    javiewSources.map {
      case (viewName, view) => new File(dst, s"${compiler.classNameFor(viewName)}.scala")
    }

  }

  private def listAllFiles(f: File): Array[File] = {
    val these = f.listFiles
    these ++ these.filter(_.isDirectory).flatMap(listAllFiles)
  }

}