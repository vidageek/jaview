package net.vidageek.jaview.sbt

import sbt._
import sbt.classpath._
import Keys._

object JaviewPlugin extends AutoPlugin {

  lazy val javiewCompile = TaskKey[Seq[File]]("jaview-compile", "Generate scala sources from .jaview files")

  def javiewSettings = Seq(
    javiewCompile in Compile := {
      val classpath = (managedClasspath in Compile).value.map(_.data)
      val loader = ClasspathUtilities.makeLoader(classpath, scalaInstance.value)
      JaviewPreCompiler.compileAll(Seq((scalaSource in Compile).value, (resourceDirectory in Compile).value), sourceManaged.value, loader)
    },
    sourceGenerators in Compile <+= (javiewCompile in Compile))

}