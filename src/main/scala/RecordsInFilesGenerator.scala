package net.jgp.books.spark.ch10.x.utils.streaming.app

import com.typesafe.scalalogging.Logger

import net.jgp.books.spark.ch10.stream_seed.{FieldType, RecordGeneratorUtils, RecordStructure, RecordWriterUtils, StreamingUtils}

object RecordsInFilesGenerator {
  val log = Logger(getClass.getName)

  val streamDuration = 60
  val batchSize = 10
  val waitTime = 5

  def main(args: Array[String]): Unit = {
    val rs1 = new RecordStructure("contact").
      add("fname", FieldType.FIRST_NAME).
      add("mname", FieldType.FIRST_NAME).
      add("lname", FieldType.LAST_NAME).
      add("age", FieldType.AGE).
      add("ssn", FieldType.SSN)

    val rs2 = new RecordStructure("employee").
      add("fname", FieldType.FIRST_NAME).
      add("mname", FieldType.FIRST_NAME).
      add("lname", FieldType.LAST_NAME).
      add("age", FieldType.AGE).
      add("ssn", FieldType.SSN)

    log.info("-> main (...)")

    val haveTwo = args.length > 0

    val start = System.currentTimeMillis()
    while (start + streamDuration * 1000 > System.currentTimeMillis()) {
      val maxRecord = RecordGeneratorUtils.getRandomInt(batchSize) + 1

      RecordWriterUtils.write(
        rs1.recordName + "_" + System.currentTimeMillis() + ".txt",
        rs1.getRecords(maxRecord, false),
        "./input/s1/"
      )

      if (haveTwo) {
        RecordWriterUtils.write(
          rs2.recordName + "_" + System.currentTimeMillis() + ".txt",
          rs2.getRecords(maxRecord, false),
          "./input/s2/"
      )
      }

      try {
        Thread.sleep(RecordGeneratorUtils.getRandomInt(waitTime * 1000) + waitTime * 1000 / 2);
      } catch {
        // Simply ignore the interruption
        case e: InterruptedException =>
      }
    }
  }
}