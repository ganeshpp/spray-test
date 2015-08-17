name := "Spray-Test"

version := "1.0"

scalaVersion := "2.11.0"

resolvers += "Scalaz Bintray Repo" at "http://dl.bintray.com/scalaz/releases"

resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
  "ch.qos.logback" % "logback-classic" % "1.1.2",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.1.0",
  "com.typesafe.akka" %% "akka-actor" % "2.3.6",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.6",
  "io.spray" %% "spray-can" % "1.3.2",
  "io.spray" %% "spray-client" % "1.3.2",
  "io.spray" %% "spray-routing" % "1.3.2",
  "io.spray" %% "spray-json" % "1.3.2",
  "org.scala-lang" % "scala-reflect" % "2.11.0",
  "org.specs2" %% "specs2" % "2.4.6" ,
  "io.spray" %% "spray-testkit" % "1.3.2" ,
  "org.scalatest" %% "scalatest" % "2.2.4" ,
  "com.typesafe.akka" %% "akka-testkit" % "2.3.6"
)

    