package net.jgp.books.spark

import net.jgp.books.spark.ch10.lab300_read_network_stream.ReadLinesFromNetworkStream
import net.jgp.books.spark.ch10.lab400_read_records_from_multiple_streams.ReadRecordFromMultipleFileStream
import net.jgp.books.spark.ch10.lab200_read_stream.ReadLinesFromFileStream

object MainApp {
  def main(args: Array[String]) = {

    val (whichCase, otherArg) = args.length match {
      case 1 => (args(0).toUpperCase, "TEXT")
      case 2 => (args(0).toUpperCase, args(1).toUpperCase)
      case _ => ("NET", "")
    }

    println(s"=========== whichCase = $whichCase, otherArg = $otherArg ===========")

    whichCase match {
      case "FILE"     => ReadLinesFromFileStream.run(otherArg) // text | schema
      case "MULTI"    => ReadRecordFromMultipleFileStream.run()
      case _          => ReadLinesFromNetworkStream.run()
    }
  }
}