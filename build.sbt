
organization := "net.vidageek"

name         := "jaview"

version in Global := "1.0"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
						"org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.1",
						"org.scala-lang" % "scala-reflect" % "2.11.1",
    					"org.scala-lang" % "scala-compiler" % "2.11.1",
						"junit" % "junit" % "4.+" % "test",
						"org.specs2" %% "specs2" % "2.3.11" % "test"
						)

EclipseKeys.withSource := true

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

