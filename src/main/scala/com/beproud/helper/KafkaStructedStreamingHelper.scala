package com.beproud.helper

import org.apache.spark.sql.DataFrame

trait KafkaStructuredStreamingHelper extends SparkHelper {
  def topicName(): String

  def bootstrapServers(): String = "localhost:9092,localhost:9093"

  def startingOffsets(): String = "earliest"

  def maxOffsetsPerTrigger(): Int = 10

  def stopGracefullyOnShutdown(): Boolean = true

  def streamDf(): DataFrame = {
    spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", bootstrapServers())
      .option("subscribe", topicName())
      .option("startingOffsets", startingOffsets())
      .option("maxOffsetsPerTrigger", maxOffsetsPerTrigger())
      .option("stopGracefullyOnShutdown", stopGracefullyOnShutdown().toString)
      .option("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      .option("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
      .load()
  }
}
