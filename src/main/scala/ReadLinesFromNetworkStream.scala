package net.jgp.books.spark.ch10.lab300_read_network_stream

import com.typesafe.scalalogging.Logger
import org.apache.spark.sql.streaming.StreamingQueryException
import scala.concurrent.TimeoutException

import net.jgp.books.spark.basic.Basic

object ReadLinesFromNetworkStream extends Basic{

  val log = Logger(getClass.getName)

  def run(): Unit = {
    log.info("-> run()")
    val spark = getSession("Lines From Network")

    val df = spark.readStream.
      format("socket").
      option("host", "localhost").
      option("port", 9999).
      load()

    val query = df.writeStream.
      // https://spark.apache.org/docs/latest/api/scala/org/apache/spark/sql/streaming/DataStreamWriter$.html
      outputMode("append").
      format("console").
      start()

    try {
      // running in one minite
      query.awaitTermination(60000)
    } catch {
      case e: StreamingQueryException  => log.error("Exception while waiting for query to end {}.", e.getMessage)
      case t: TimeoutException         => log.error("A timeout exception has occured: {}", t.getMessage)
    } //finally {}

    log.info("Query status: {}", query.status)
    log.info("<- run()")
  }
}