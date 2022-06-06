package net.jgp.books.spark.ch10.stream_seed

import com.typesafe.scalalogging.Logger

import net.jgp.books.spark.ch10.stream_seed.FieldType._

class RecordStructure(val recordName: String) {

  val log = Logger(getClass.getName)

  var columns: Seq[(String, ColumnProperty)] = Seq.empty

  def add(columnName: String, recordType: FieldType): RecordStructure = {
    columns = columns :+ (columnName -> ColumnProperty(recordType, None))
    this
  }

  def add(columnName: String, recordType: FieldType, option: String): RecordStructure = {
    columns = columns :+ (columnName -> ColumnProperty(recordType, Some(option)))
    this
  }

  def getRecords(recordCount: Int, header: Boolean): String = {
    val record = new StringBuilder

    if (header) {
      var row: Seq[String] = Seq.empty
      columns.foreach({
        case (name, _) => { row = row :+ name }
      })
      record ++= row.mkString("", ",", "\n")
    }

    (0 to recordCount).foreach( _ => {

      var row: Seq[String] = Seq.empty
      columns.foreach({
        case (_, prop) => {
          prop.recordType match {
            case FIRST_NAME => row = row :+ RecordGeneratorUtils.getFirstName
            case LAST_NAME  => row = row :+ RecordGeneratorUtils.getLastName
            case AGE        => row = row :+ s"${RecordGeneratorUtils.getRandomInt(RecordGeneratorK.MAX_AGE)}"
            case SSN        => row = row :+ RecordGeneratorUtils.getRandomSSN
          }
        }
      })
      record ++= row.mkString("", ",", "\n")
    })

    val result = record.toString
    log.info("Generated data:\n{}", result);
    result
  }
}