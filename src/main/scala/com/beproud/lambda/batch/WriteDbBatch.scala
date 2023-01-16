package com.beproud.lambda.batch

import com.beproud.config.Configs
import com.beproud.domain.user.{ServingUser, ServingUserRepository}
import com.beproud.dsl.db.withDB
import com.beproud.helper.SparkHelper
import com.beproud.lambda.batch.ReadS3Batch.spark
import org.apache.hadoop.shaded.org.jline.keymap.KeyMap.display

import scala.collection.mutable

object WriteDbBatch extends SparkHelper {
  override def run(args: Array[String]): Unit = {
    println("WriteDbBatch")

    val results = mutable.ArrayBuffer.empty[User]
    withDB("db1") { c =>
      val pstmt =
        c.prepareStatement("SELECT `wallet_address`,`nickname`,`is_active` FROM `user`")

      val rs = pstmt.executeQuery()
      while (rs.next()) {
        results.append(
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

    val repository = new ServingUserRepository()
    results.map(toServingUser)
      .foreach(repository.insert)
  }

  private def toServingUser(user: User): ServingUser = {
    ServingUser(user.walletAddress, user.nickname, user.isActive)
  }
}
