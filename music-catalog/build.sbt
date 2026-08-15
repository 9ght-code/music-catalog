ThisBuild / scalaVersion := "3.8.4"
ThisBuild / organization := "com.example"

lazy val root = (project in file("."))
  .settings(
    name := "music-catalog",
    libraryDependencies ++= Seq(
      "org.postgresql" % "postgresql" % "42.7.5",
      "org.scalameta" %% "munit" % "1.0.4" % Test
    )
  )
