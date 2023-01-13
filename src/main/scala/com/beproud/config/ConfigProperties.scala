package com.beproud.config

case class AppProperties(
                          phase: String,
                          submit: AppSubmitProperties,
                          s3: AppS3Properties,
                          db: AppDbProperties,
                        )

case class AppSubmitProperties(master: String)

case class AppS3Properties(dataLake: String)

case class AppDbProperties(
                            driver: String,
                            username: String,
                            password: String,
                            urlProperty: String,
                            cachePrepStmts: String,
                            prepStmtCacheSize: String,
                            prepStmtCacheSqlLimit: String,
                            minimumIdle: Int,
                            maximumPoolSize: Int,
                            connectionTimeout: Int,
                          )
