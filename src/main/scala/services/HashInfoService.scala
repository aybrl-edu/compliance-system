package services

import models.UserDataInfo
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.functions.{col, when}

class HashInfoService extends IService {
  override def execute(ds : Dataset[UserDataInfo], uid  : Long): Dataset[UserDataInfo] = {
    val rand = new scala.util.Random
    val UUID : String = s"${uid}-${rand.nextInt()}"

    import workers.HDFSFileManager.sparkSession.implicits._

    // Testing new approach
    /*val update = ds.filter(user => user.idClient == uid)
      .map(user => {
        user.firstName -> UUID
        user.lastName -> UUID
        user.address -> UUID
      }).as[UserDataInfo]*/

    val updateDS : Dataset[UserDataInfo] = ds.withColumn("idClient", when(col("idClient") === uid, uid))
      .withColumn("FirstName", when(col("idClient") === uid, UUID).otherwise(col("FirstName")))
      .withColumn("LastName", when(col("idClient") === uid, UUID).otherwise(col("LastName")))
      .withColumn("Address", when(col("idClient") === uid, UUID).otherwise(col("Address")))
      .as[UserDataInfo]

    updateDS
  }

}
