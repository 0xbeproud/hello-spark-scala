package com.beproud.domain

import java.sql.PreparedStatement
import scala.collection.mutable.ArrayBuffer

abstract class AbstractRepository extends Serializable {
  val DATABASE_NAME: String
  val TABLE_NAME: String

  def execute(preparedStatement: PreparedStatement): Array[Int] = {
    execute(preparedStatement, ArrayBuffer.empty)
  }

  def execute(preparedStatement: PreparedStatement, logs: ArrayBuffer[String]): Array[Int] = {
    try {
      val result = preparedStatement.executeBatch()
      preparedStatement.clearBatch()
      result
    } catch {
      case e: Throwable => throw e
    }
  }
}
