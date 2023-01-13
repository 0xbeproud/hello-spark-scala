package com.beproud.lambda.batch

import com.beproud.config.Configs
import com.beproud.helper.SparkSessionWrapper

object ReadS3Batch extends SparkSessionWrapper {
  override def run(args: Array[String]): Unit = {
    println("ReadS3Batch")

    val dataLake = Configs.app.s3.dataLake
    import spark.implicits._
    val df = spark.read.parquet(s"${dataLake}/sample")
    df.printSchema()
    df.show(10, true)
    val selectDf = df.select("idOf4Byte", "bytesSignature")
    selectDf.show(10, true)
  }
}
