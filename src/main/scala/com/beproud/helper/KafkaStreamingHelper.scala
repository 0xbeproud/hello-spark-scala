package com.beproud.helper

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.sql.DataFrame
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka010.{ConsumerStrategies, KafkaUtils, LocationStrategies}

trait KafkaStreamingHelper extends StreamingHelper {
  def topics(): Set[String]

  def consumerGroupId(): String

  def bootstrapServers(): String

  def offsetBucket(): String

  def offsetKey(): String

  def consumeMode(): String

  def createDirectStream(
                          topics: Set[String],
                          consumerGroupId: String,
                          bootstrapServers: String,
                          offsetBucket: String,
                          offsetKey: String,
                          mode: String = "resume"): InputDStream[ConsumerRecord[String, String]] = {

    val params = Map[String, Object](
      "bootstrap.servers" -> bootstrapServers,
      "key.serializer" -> classOf[StringDeserializer],
      "key.deserializer" -> classOf[StringDeserializer],
      "value.serializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> consumerGroupId,
      "auto.offset.reset" -> "earliest",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    KafkaUtils.createDirectStream[String, String](
      ssc,
      LocationStrategies.PreferConsistent,
      ConsumerStrategies.Subscribe[String, String](topics, params)
    )
  }

  def stream(): InputDStream[ConsumerRecord[String, String]] = {
    createDirectStream(topics(), consumerGroupId(), bootstrapServers(), offsetBucket(), offsetKey(), consumeMode())
  }
}
