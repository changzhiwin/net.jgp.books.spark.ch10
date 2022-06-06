package net.jgp.books.spark.ch10.lab400_read_records_from_multiple_streams

import org.apache.spark.sql.Row
import org.apache.spark.sql.ForeachWriter
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.streaming.StreamingQuery
import org.apache.spark.sql.SparkSession
import java.lang.System
import com.typesafe.scalalogging.Logger

import net.jgp.books.spark.basic.Basic

object ReadRecordFromMultipleFileStream extends Basic {

  val log = Logger(getClass.getName)

  def run(): Unit = {

    log.info("-> run()")

    val spark = getSession("Read lines over a file stream")

    val schema = StructType.fromDDL("fname STRING, mname STRING, lname STRING, age INTEGER, ssn STRING")

    val queryStream1 = readStreamByLocation(spark, schema, "./input/s1")

    val queryStream2 = readStreamByLocation(spark, schema, "./input/s2")

    var iterationCount = 0
    val startProcessing = System.currentTimeMillis()
    while(queryStream1.isActive && queryStream2.isActive) {
      iterationCount += 1

      log.error("Pass #{}", iterationCount)

      if (startProcessing + 60000 < System.currentTimeMillis()) {
        queryStream1.stop
        queryStream2.stop
      }
      
      try{
        Thread.sleep(5000)
      } catch {
        case e: InterruptedException =>
      }
      
    }

    log.info("<- run()")

  }

  private def readStreamByLocation(spark: SparkSession, schema: StructType,  path: String): StreamingQuery = {
    val df = spark.readStream.
      format("csv").
      schema(schema).
      load(path)

    df.writeStream.
      outputMode("append").
      // ref: https://spark.apache.org/docs/latest/api/scala/org/apache/spark/sql/ForeachWriter.html
      foreach(new ForeachWriter[Row] {

        def process(record: Row): Unit = {
          if (record.length == 5) {
            val age = record.getInt(3)

            if (age < 13) {
              log.error("On stream #{}: {} is a teen, they are {} yrs old.",
                  path,
                  record.getString(0),
                  age)
            } else if (age > 12 && age < 20) {
              log.error("On stream #{}: {} is a teen, they are {} yrs old.",
                  path,
                  record.getString(0),
                  age)
            } else {
              log.error("On stream #{}: {} is a senior, they are {} yrs old.",
                  path,
                  record.getString(0),
                  age)
            }
          }
        }

        def open(partitionId: Long, version: Long): Boolean = true

        def close(errorOrNull: Throwable): Unit = {}
      }).
      start()
  }
}