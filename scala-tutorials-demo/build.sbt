ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "scala-tutorials-demo",
    idePackagePrefix := Some("com.github.veronikagalinova"),
    libraryDependencies ++= Seq(
      "org.typelevel" %% "cats-effect" % "3.4.6",
      "org.typelevel" %% "cats-core" % "2.9.0",
      "org.typelevel" %% "cats-mtl" % "1.3.0",
      "eu.timepit" %% "refined" % "0.10.1"
    )
  )
