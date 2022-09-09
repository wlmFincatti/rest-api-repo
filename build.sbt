ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

val http4sVersion = "0.23.15"
val skunkVersion = "0.3.1"
val CirceVersion = "0.14.2"
val log4catsVersion = "2.3.1"
val LogbackVersion = "1.2.11"

// Only necessary for SNAPSHOT releases
resolvers += Resolver.sonatypeRepo("snapshots")

lazy val root = (project in file("."))
  .settings(
    name := "rest-api-repo"
  )

libraryDependencies ++= Seq(
  "org.http4s" %% "http4s-core" % http4sVersion,
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-ember-server" % http4sVersion,
  "org.http4s" %% "http4s-ember-client" % http4sVersion,

  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-core" % CirceVersion,
  "io.circe" %% "circe-generic" % CirceVersion,

  "org.tpolecat" %% "skunk-core" % skunkVersion,
  "org.tpolecat" %% "skunk-circe" % skunkVersion,

  "ch.qos.logback" % "logback-classic" % LogbackVersion % Runtime,
)

