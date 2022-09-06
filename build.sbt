ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val http4sVersion = "1.0.0-M36"
val skunkVersion = "0.3.1"
val CirceVersion = "0.14.0-M5"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    name := "rest-api-repo"
  )

libraryDependencies ++= Seq(
//  "org.tpolecat" %% "skunk-core" % "0.2.3",
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,

  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "io.circe" %% "circe-generic" % CirceVersion,

  "org.tpolecat" %% "skunk-core" % skunkVersion,
  "org.tpolecat" %% "skunk-circe" % skunkVersion
)