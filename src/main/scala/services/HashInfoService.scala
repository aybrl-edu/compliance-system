package services

import models.UserDataInfo
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.{col, when}

class HashInfoService extends IService {
  override def execute(ds : Dataset[UserDataInfo], uid  : Long): Dataset[UserDataInfo] = {
    val rand = new scala.util.Random
    val UUID : String = s"${uid}-${rand.nextInt()}"

    import workers.HDFSFileManager.sparkSession.implicits._

    val updateDS : Dataset[UserDataInfo] = ds
      .withColumn("idClient", when(col("idClient") === uid, uid).otherwise(col("idClient")))
      .withColumn("FirstName", when(col("idClient") === uid, UUID).otherwise(col("FirstName")))
      .withColumn("LastName", when(col("idClient") === uid, UUID).otherwise(col("LastName")))
      .withColumn("Address", when(col("idClient") === uid, UUID).otherwise(col("Address")))
      .as[UserDataInfo]

    updateDS
  }

}
