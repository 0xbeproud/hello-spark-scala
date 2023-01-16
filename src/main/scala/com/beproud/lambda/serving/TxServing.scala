package com.beproud.lambda.serving

import com.beproud.helper.SparkHelper
import com.typesafe.config.ConfigFactory

object TxServing extends SparkHelper {
  override def run(args: Array[String]): Unit = {
    println("TxServing")
  }
}
