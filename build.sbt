
organization := "net.vidageek"

name         := "jaview"

version in Global := "1.0"

scalaVersion := "2.11"

libraryDependencies ++= Seq(
              "org.parboiled" %% "parboiled-scala" % "1.1.6",
   						"org.specs2" %% "specs2" % "2.3.11" % "test"
							)

EclipseKeys.withSource := true

EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

