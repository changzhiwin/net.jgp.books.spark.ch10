# Purpose
pure scala version of https://github.com/jgperrin/net.jgp.books.spark.ch10

# Environment
- Java 11
- Scala 2.13.8
- Spark 3.2.1

# How to run
## 1, sbt package, in project root dir
When success, there a jar file at ./target/scala-2.13. The name is `main-scala-ch10_2.13-1.0.jar` (the same as name property in sbt file)

## 2.1, source of file stream
```
cd YOUR_PROJECT_DIR
sbt
> runMain net.jgp.books.spark.ch10.x.utils.streaming.app.RecordsInFilesGenerator [two-stream]
```

## 2.2, submit jar file, in project root dir
```
// common jar, need --jars option
$ YOUR_SPARK_HOME/bin/spark-submit \
  --class net.jgp.books.spark.MainApp \
  --master "local[*]" \
  --jars jars/scala-logging_2.13-3.9.4.jar \
  target/scala-2.13/main-scala-ch10_2.13-1.0.jar MULTI

// fat jar, no need --jar option
$ YOUR_SPARK_HOME/bin/spark-submit \
--class net.jgp.books.spark.MainApp \
--master "local[*]" \
target/scala-2.13/main/scala/ch10-assembly-1.0.jar
```

## 3, print

### Case: two stream
```
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Maverick is a senior, they are 109 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Corey is a teen, they are 5 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Desmond is a senior, they are 110 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Kira is a senior, they are 54 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Adonis is a teen, they are 8 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Kenny is a senior, they are 93 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Leilani is a teen, they are 0 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Colten is a senior, they are 94 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s2: Demi is a teen, they are 4 yrs old.

22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Jasmine is a senior, they are 68 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Pearl is a senior, they are 20 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Drew is a senior, they are 88 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Zyaire is a senior, they are 30 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Castiel is a senior, they are 22 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Brittany is a senior, they are 33 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Cassidy is a senior, they are 36 yrs old.
22/06/06 22:08:35 ERROR ReadRecordFromMultipleFileStream$: On stream #./input/s1: Brodie is a senior, they are 46 yrs old.
```

## 4, Some diffcult case

### Download jar
When I use [scala-logging](https://github.com/lightbend/scala-logging), I need download jar file.
> https://mvnrepository.com/

### Creating Scala Fat Jars for Spark
Refer to some links [for scala 2.13](https://github.com/diegopacheco/scala-playground/blob/master/fatjar-assembly-sbt-1.5.0/build.sbt), [on spark](https://queirozf.com/entries/creating-scala-fat-jars-for-spark-on-sbt-with-sbt-assembly-plugin), and [fat jar](https://www.baeldung.com/scala/sbt-fat-jar)