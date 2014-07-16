
import sbt._
import Keys._
import IO._

object JaviewBuild extends Build {
  import Dependencies._
  import TestDependencies._

  lazy val root = Project(
    id = "jaview",
    base = file("."),
    aggregate = Seq(core))

  lazy val core = Project(
    id = "jaview-core",
    base = file("core"),
    settings = (commonSettings ++ deps(scalaReflect, scalaCompiler, scalaParserCombinator)))
    
  lazy val render = Project(
 		id = "jaview-render",
 		base = file("render"),
 		settings = (commonSettings))

  lazy val commonSettings: Seq[Setting[_]] = Defaults.defaultSettings ++ Seq(
    organization := "net.vidageek",
    version := "1.0",
    scalaVersion := "2.11.1",
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-g:vars", "-feature", "-language:_"),
    libraryDependencies ++= Seq(junit, specs2))

  private def deps(d: ModuleID*): Seq[Setting[_]] = Seq(libraryDependencies ++= d.toSeq)

  object Dependencies {
    val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.11.1"
    val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.11.1"
    val scalaParserCombinator = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1"
  }

  object TestDependencies {
    val junit = "junit" % "junit" % "4.+" % "test"
    val specs2 = "org.specs2" %% "specs2" % "2.3.11" % "test"
  }
}
