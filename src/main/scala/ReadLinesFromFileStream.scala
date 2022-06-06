package net.jgp.books.spark.ch10.lab200_read_stream

import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.streaming.StreamingQueryException
import org.apache.spark.sql.streaming.StreamingQuery
import scala.concurrent.TimeoutException
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.DataFrame


import net.jgp.books.spark.basic.Basic

object ReadLinesFromFileStream extends Basic{

  val log = Logger(getClass.getName)

  def run(mode: String): Unit = {
    log.info("-> run()")

    val spark = getSession("Read lines over a file stream")

    val query = mode match {
      case "TEXT"  => useOnlyText(spark, "./input/s1")
      case _       => useSchema(spark, "./input/s1")
    }

    try {
      // running in one minite
      query.awaitTermination(20000)
    } catch {
      case e: StreamingQueryException  => log.error("Exception while waiting for query to end {}.", e.getMessage)
      case t: TimeoutException         => log.error("A timeout exception has occured: {}", t.getMessage)
    } //finally {}

    log.info("<- run()")
  }

  private def useOnlyText(spark: SparkSession, path: String): StreamingQuery = {

    val df = spark.readStream.
      format("text").
      load(path)

    df.writeStream.
      outputMode("append").
      format("console").
      option("truncate", false).
      option("numRows", 3).
      start()
  }

  private def useSchema(spark: SparkSession, path: String): StreamingQuery = {

    val schema = "fname STRING, mname STRING, lname STRING, age INTEGER, ssn STRING"
    val df = spark.readStream.
      format("csv").
      schema(schema).
      load(path)
    
    df.writeStream.
      outputMode("append").
      format("console").
      start()
  }
}