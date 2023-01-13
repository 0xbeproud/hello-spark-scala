package com.beproud.dsl

import com.beproud.config.Configs
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

import java.sql.Connection
import javax.sql.DataSource

package object db {
  private val dataSources = scala.collection.mutable.Map[String, HikariDataSource]()

  def withDB[R](dbName: String)(block: Connection => R): R = {
    withDB(getDataSource(dbName)) {
      block
    }
  }

  def withDB[R](ds: DataSource)(block: Connection => R): R = {
    val connection = ds.getConnection()

    try {
      block(connection)
    } catch {
      case e: Throwable => throw e
    } finally {
      connection.commit()
      connection.close()
    }
  }

  private def getDataSource(dbName: String): DataSource = {
    Class.forName("com.mysql.jdbc.Driver")

    this.synchronized {
      if (!dataSources.contains(dbName)) {
        val db = Configs.app.db
        val url = Configs.config.getString(s"app.db.host.$dbName")
        val urlProperty = db.urlProperty

        val dbConf = new HikariConfig()
        dbConf.setJdbcUrl(s"$url?$urlProperty")
        dbConf.setUsername(db.username)
        dbConf.setPassword(db.password)
        dbConf.addDataSourceProperty("cachePrepStmts", db.cachePrepStmts)
        dbConf.addDataSourceProperty("prepStmtCacheSize", db.prepStmtCacheSize)
        dbConf.addDataSourceProperty("prepStmtCacheSqlLimit", db.prepStmtCacheSqlLimit)
        dbConf.setMinimumIdle(db.minimumIdle)
        dbConf.setMaximumPoolSize(db.maximumPoolSize)
        dbConf.setConnectionTimeout(db.connectionTimeout)
        dbConf.addDataSourceProperty("useServerPrepStmts", "true")
        dbConf.setAutoCommit(false)

        dbConf.setPoolName(dbName)

        dataSources.put(dbName, new HikariDataSource(dbConf))
      }

      dataSources(dbName)
    }
  }
}
