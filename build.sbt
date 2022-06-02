//
name := "main/scala/ch10"

version := "1.0"
scalaVersion := "2.13.8"

// The “provided” keyword indicates that the dependency is provided by the runtime, so there’s no need to include it in the JAR file.
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.2.1" % "provided",
  "org.apache.spark" %% "spark-core" % "3.2.1" % "provided",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.4"
)

Compile / mainClass  := Some("net.jgp.books.spark.MainApp")
assembly / mainClass := Some("net.jgp.books.spark.MainApp")

// assemblyJarName in assembly := "change name.jar"

// force force sbt to use a specific repository
// refer: https://andres.jaimes.net/scala/how-to-create-a-fat-jar/
// externalResolvers := Seq("central repository".at("https://repo1.maven.org/maven2/"))

/**
 * we may encounter an error caused by the default deduplicate merge strategy. 
 * In most cases, this is caused by files in the META-INF directory.
*/
assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}