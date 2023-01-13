package com.beproud.lambda.stream

import com.beproud.helper.SparkSessionWrapper

object TxStream extends SparkSessionWrapper {
  override def run(args: Array[String]): Unit = {
    println("TxStream")
  }
}
