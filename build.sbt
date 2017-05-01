name := """play-reactivemongo-time-series"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.enragedginger" %% "akka-quartz-scheduler" % "1.6.0-akka-2.4.x",
  "net.codingwell" %% "scala-guice" % "4.1.0",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.12.1",
  "joda-time" % "joda-time" % "2.9.9",
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test)

fork in run := true