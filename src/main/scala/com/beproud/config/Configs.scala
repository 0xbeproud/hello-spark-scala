package com.beproud.config

import com.typesafe.config.{Config, ConfigFactory}
import pureconfig.ConfigSource

object Configs extends Serializable {
  private lazy val source: ConfigSource = ConfigSource.resources(System.getProperty("custom.config.path"))
  lazy val config: Config = ConfigFactory.load(System.getProperty("custom.config.path"))

  import pureconfig.generic.auto._

  lazy val app: AppProperties = source.at("app").loadOrThrow[AppProperties]
}
