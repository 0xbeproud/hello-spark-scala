package com.beproud.helper

import com.beproud.config.Configs
import com.beproud.lambda.batch.ReadDbBatch.spark
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

trait SparkSessionWrapper {
  lazy val appName: String = this.getClass.getName.stripSuffix("$")

  lazy implicit val spark: SparkSession = {

    lazy implicit val sparkConf: SparkConf = {
      val sparkConf = new SparkConf()

      sparkConf.set("spark.streaming.stopGracefullyOnShutdown", "true")

      // TODO delete. not working in here. substitute by step.sh parameter
      sparkConf.set("spark.yarn.maxAppAttempts", "1")

      sparkConf.set("spark.eventLog.rotation.enabled", "true")
      sparkConf.set("spark.eventLog.rotation.interval", "600")
      sparkConf.set("spark.eventLog.rotation.maxFilesToRetain", "6")
      //      sparkConf.set("spark.streaming.kafka.consumer.poll.ms", "10000")

      val customConfig = System.getProperty("custom.config.path", "")
      if (customConfig.nonEmpty) {
        val property = System.getProperty("spark.executor.extraJavaOptions", "")
        sparkConf.set("spark.executor.extraJavaOptions", s"$property -Dcustom.config.path=${customConfig}")
      }
      sparkConf
    }

    val phase = Configs.app.phase
    val master = Configs.app.submit.master
    if (phase == "prod") {
      SparkSession.builder().master(master).config(sparkConf).enableHiveSupport().getOrCreate()
    } else {
      SparkSession.builder().master(master).config(sparkConf).getOrCreate()
    }
  }

  def run(args: Array[String]): Unit

  def stop(): Unit = {
    spark.stop()
  }

  private def beforeRun(args: Array[String]): Unit = {
    val phase = args(0)
    val configFile = s"application-${phase}.conf"
    System.setProperty("custom.config.path", configFile)
  }

  private def afterRun(args: Array[String]): Unit = {
  }

  def main(args: Array[String]): Unit = {
    Logger.getLogger("org.apache.spark").setLevel(Level.WARN)

    if (args.length == 0) {
      throw new Exception("invalid arguments")
    }

    beforeRun(args)
    run(args)
    afterRun(args)

    stop()
  }
}
