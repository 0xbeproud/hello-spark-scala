package com.beproud.lambda.serving

import com.beproud.helper.SparkSessionWrapper
import com.typesafe.config.ConfigFactory

object TxServing extends SparkSessionWrapper {
  override def run(args: Array[String]): Unit = {
    println("TxServing")
  }
}
