import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "2.13.12"

lazy val hello = project
  .in(file("."))
  .settings(
    name := "Zionomicon",
    libraryDependencies += "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
    libraryDependencies += "dev.zio" %% "zio" % "2.0.19"
  )