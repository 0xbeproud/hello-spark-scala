package com.beproud.lambda.stream

import com.beproud.config.Configs
import com.beproud.helper.{KafkaStreamingHelper, KafkaStructuredStreamingHelper}
import org.apache.spark.sql.streaming.Trigger.ProcessingTime
import org.apache.spark.sql.types.StructType

object TxStructuredStream extends KafkaStructuredStreamingHelper {
  override def topicName(): String = "sample-topic"

  override def run(args: Array[String]): Unit = {
    println("TxStream")
    println(Configs.app.stream.kafka.consume.mode)
    println(Configs.app.stream.kafka.topic.useList(0))

    val df = streamDf()
    df.printSchema()

    import spark.implicits._
    val values = df.select($"value".cast("STRING").as("json"))

    values.writeStream
      .trigger(ProcessingTime("5 seconds"))
      .outputMode("append")
      .format("console")
      .start()
      .awaitTermination()
  }
}
