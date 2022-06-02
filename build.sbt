//
name := "main/scala/ch10"

version := "1.0"

scalaVersion := "2.13.8"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.2.1",
  "org.apache.spark" %% "spark-core" % "3.2.1",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
)

