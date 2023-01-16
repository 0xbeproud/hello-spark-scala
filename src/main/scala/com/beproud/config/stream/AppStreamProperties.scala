package com.beproud.config.stream

import com.typesafe.config.Config

import scala.concurrent.duration.Duration

case class AppStreamMonitoringProperties(
                                          maxDelay: String,
                                          notifyInterval: String,
                                        )

case class AppStreamKafkaConsumeProperties(
                                            mode: String,
                                          )

case class AppStreamKafkaTopicProperties(
                                          useList: List[String],
                                        )

case class AppStreamKafkaProperties(
                                     consume: AppStreamKafkaConsumeProperties,
                                     topic: AppStreamKafkaTopicProperties,
                                   )

case class AppStreamProperties(
                                batchDuration: String,
                                monitoring: AppStreamMonitoringProperties,
                                kafka: AppStreamKafkaProperties,
                              )
