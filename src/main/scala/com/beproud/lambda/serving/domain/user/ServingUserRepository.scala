package com.beproud.lambda.serving.domain.user

import com.beproud.dsl.db.withDB
import com.beproud.repository.AbstractRepository

class ServingUserRepository extends AbstractRepository {

  override val DATABASE_NAME: String = "db1"
  override val TABLE_NAME: String = "serving_user"

  def insert(entity: ServingUser): Unit = {
    withDB(DATABASE_NAME) { c =>
      val pstmt = c.prepareStatement(
        s"INSERT INTO ${TABLE_NAME} (`wallet_address`, `nickname`, `is_active`) VALUES (?, ?, ?)"
      )
      pstmt.setString(1, entity.walletAddress)
      pstmt.setString(2, entity.nickname)
      pstmt.setBoolean(3, entity.isActive)

      try {
        pstmt.execute()
      } catch {
        case e: Throwable => throw e
      } finally {
        pstmt.close()
      }
    }
  }
}
