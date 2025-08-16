import sbt.Keys.libraryDependencies

ThisBuild / scalaVersion := "2.13.12"
val zioVersion = "2.0.19"

lazy val hello = project
  .in(file("."))
  .settings(
    name := "Zionomicon",
    libraryDependencies ++= Seq(
      "org.scala-lang" %% "toolkit-test" % "0.1.7" % Test,
      "dev.zio" %% "zio" % zioVersion,
      "dev.zio" %% "zio-test" % zioVersion,
      "dev.zio" %% "zio-test-sbt" % zioVersion
    )
  )