
import sbt._
import Keys._
import IO._
import xerial.sbt.Sonatype._
import xerial.sbt.Sonatype.SonatypeKeys._

object JaviewBuild extends Build {
  import Dependencies._
  import TestDependencies._

  lazy val root = Project(
    id = "jaview",
    base = file("."),
    aggregate = Seq(core, render, javiewSbt))

  lazy val core = Project(
    id = "jaview-core",
    base = file("core"),
    settings = (commonSettings ++ deps(scalaReflect, scalaCompiler, scalaParserCombinator)))
    
  lazy val render = Project(
 		id = "jaview-render",
 		base = file("render"),
 		settings = (commonSettings)).
 		dependsOn(core)

  lazy val javiewSbt = Project(
    id = "jaview-sbt",
    base = file("sbt"),
    settings = (pluginSettings))

  lazy val commonSettings: Seq[Setting[_]] = Defaults.defaultSettings ++ sonatypeSettings ++ keepSonatypeHappy ++ Seq(
    organization := "net.vidageek",
    scalaVersion := "2.11.2")

  lazy val pluginSettings: Seq[Setting[_]] = Defaults.defaultSettings ++ sonatypeSettings ++ keepSonatypeHappy ++ Seq(
      sbtPlugin := true,
      scalaVersion := "2.10.4")

  lazy val keepSonatypeHappy: Seq[Setting[_]] = Seq(
    version := "0.2",
    publishMavenStyle := true,
    licenses := Seq("MIT" -> url("http://opensource.org/licenses/mit-license.php")),
    homepage := Some(url("http://projetos.vidageek.net/jaview")),
    pomExtra := (
  <scm>
    <url>git@github.com:vidageek/jaview.git</url>
    <connection>scm:git:git@github.com:vidageek/jaview.git</connection>
  </scm>
  <developers>
    <developer>
      <id>jonasabreu</id>
      <name>Jonas Abreu</name>
      <url>http://www.vidageek.net</url>
    </developer>
  </developers>),
    scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked", "-g:vars", "-feature", "-language:_"),
    libraryDependencies ++= Seq(junit, specs2))
    
  private def deps(d: ModuleID*): Seq[Setting[_]] = Seq(libraryDependencies ++= d.toSeq)

  object Dependencies {
    val scalaReflect = "org.scala-lang" % "scala-reflect" % "2.11.2"
    val scalaCompiler = "org.scala-lang" % "scala-compiler" % "2.11.2"
    val scalaParserCombinator = "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1"
  }

  object TestDependencies {
    val junit = "junit" % "junit" % "4.+" % "test"
    val specs2 = "org.specs2" %% "specs2" % "2.3.11" % "test"
  }
}
