package com.beproud.config.stream

import com.typesafe.config.Config

case class AppStreamMonitoringProperties(
                                          maxDelay: String,
                                          notifyInterval: String,
                                        )

case class AppStreamKafkaProperties(
                                     maxDelay: String,
                                     notifyInterval: String,
                                   )

case class AppStreamProperties(
                                batchDuration: String,
                                monitoring: AppStreamMonitoringProperties,
                                kafka: Config,
                              )
