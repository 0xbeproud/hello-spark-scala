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

    stream().foreachRDD(rdd => println(rdd))
  }

  override def topics(): Set[String] = Array[String]("sample-topic").toSet

  override def consumerGroupId(): String = "sample-group-id"

  override def bootstrapServers(): String = "localhost:9092,localhost:9093"

  override def offsetBucket(): String = "data-lake"

  override def offsetKey(): String = "sample-offset-key"

  override def consumeMode(): String = "resume"
}
