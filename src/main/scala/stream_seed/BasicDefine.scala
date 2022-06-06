package net.jgp.books.spark.ch10.stream_seed

object FieldType extends Enumeration {
  type FieldType = Value
  val FIRST_NAME, LAST_NAME, AGE, SSN = Value

  // not support!!!
  //ID, TITLE, LINKED_ID, DATE_LIVING_PERSON, CONTEMPORARY_YEAR, RATING, NAME
}

import FieldType._

case class ColumnProperty(recordType: FieldType, option: Option[String])

class RecordGeneratorException(message: String, e: Exception) extends Exception(message, e)

object RecordGeneratorK {
  val MAX_ID: Int = 60000
  val MAX_AGE: Int = 115
}