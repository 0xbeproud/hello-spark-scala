package com.beproud.lambda.batch

import com.beproud.config.Configs
import com.beproud.dsl.db.withDB
import com.beproud.helper.SparkSessionWrapper

import scala.collection.mutable

case class User(walletAddress: String, nickname: String, isActive: Boolean)
object ReadDbBatch extends SparkSessionWrapper {
  override def run(args: Array[String]): Unit = {
    println("ReadDbBatch")

    val result = mutable.ArrayBuffer.empty[User]
    withDB("db1") { c =>
      val pstmt =
        c.prepareStatement("SELECT `wallet_address`,`nickname`,`is_active` FROM `user`")

      val rs = pstmt.executeQuery()
      while (rs.next()) {
        result.append(
          User(
            rs.getString(1),
            rs.getString(2),
            rs.getBoolean(3)
          )
        )
      }

      rs.close()
      pstmt.close()
    }

    println(result)
  }
}
