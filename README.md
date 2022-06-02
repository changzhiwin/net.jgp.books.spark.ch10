# Purpose
pure scala version of https://github.com/jgperrin/net.jgp.books.spark.ch10

# Environment
- Java 11
- Scala 2.13.8
- Spark 3.2.1

# How to run
## 1, sbt package, in project root dir
When success, there a jar file at ./target/scala-2.13. The name is `main-scala-ch10_2.13-1.0.jar` (the same as name property in sbt file)

## 2, submit jar file, in project root dir
```
// common jar, need --jars option
$ YOUR_SPARK_HOME/bin/spark-submit \
  --class net.jgp.books.spark.MainApp \
  --master "local[*]" \
  --jars jars/scala-logging_2.13-3.9.4.jar \
  target/scala-2.13/main-scala-ch10_2.13-1.0.jar

// fat jar, no need --jar option
$ YOUR_SPARK_HOME/bin/spark-submit \
--class net.jgp.books.spark.MainApp \
--master "local[*]" \
target/scala-2.13/main/scala/ch10-assembly-1.0.jar
```

## 3, print

### Case:

## 4, Some diffcult case

### Download jar
When I use [scala-logging](https://github.com/lightbend/scala-logging), I need download jar file.
> https://mvnrepository.com/

### Creating Scala Fat Jars for Spark
Refer to some links [for scala 2.13](https://github.com/diegopacheco/scala-playground/blob/master/fatjar-assembly-sbt-1.5.0/build.sbt), [on spark](https://queirozf.com/entries/creating-scala-fat-jars-for-spark-on-sbt-with-sbt-assembly-plugin), and [fat jar](https://www.baeldung.com/scala/sbt-fat-jar)