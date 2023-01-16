package com.beproud.helper

import com.beproud.config.Configs
import org.apache.spark.streaming.{Duration, StreamingContext}


trait StreamingHelper extends SparkHelper {
  private def batchDuration(): Duration = Duration(Configs.config.getDuration("app.stream.batch-duration").toMillis)

  lazy val ssc: StreamingContext = new StreamingContext(sc, this.batchDuration())

  private def beforeRun(args: Array[String]): Unit = {
    val phase = args(0)
    val configFile = s"application-${phase}.conf"
    System.setProperty("custom.config.path", configFile)
  }

  private def afterRun(args: Array[String]): Unit = {
  }

  override def main(args: Array[String]): Unit = {
    beforeRun(args)

    run(args)

    ssc.start()

    //    SparkStreamingMonitor.run(ssc, appName)
    //    shutdownMonitor()

    afterRun(args)
  }
}
