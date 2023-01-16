package com.beproud.lambda.stream

import com.beproud.config.Configs
import com.beproud.helper.{KafkaStreamingHelper, SparkHelper, StreamingHelper}
import org.apache.spark.TaskContext
import org.apache.spark.sql.streaming.Trigger.ProcessingTime
import org.apache.spark.streaming.kafka010.{HasOffsetRanges, OffsetRange}

object TxStream extends KafkaStreamingHelper {
  override def run(args: Array[String]): Unit = {
    println("TxStream")
    println(Configs.app.stream.kafka.consume.mode)
    println(Configs.app.stream.kafka.topic.useList(0))

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", "localhost:9092,localhost:9093")
      .option("subscribe", "sample-topic")
      .load()

    import spark.implicits._
    val values = df.select($"value".cast("STRING").as("json"))

    values.writeStream
      .trigger(ProcessingTime("5 seconds"))
      .outputMode("append")
      .format("console")
      .start()
      .awaitTermination()
  }

  override def topics(): Set[String] = Array[String]("sample-topic").toSet

  override def consumerGroupId(): String = "sample-group-id"

  override def bootstrapServers(): String = "kafka:9092,kafka-1:9093"

  override def offsetBucket(): String = "data-lake"

  override def offsetKey(): String = "sample-offset-key"

  override def consumeMode(): String = "resume"
}
