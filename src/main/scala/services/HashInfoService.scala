package services

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, when}



class HashInfoService extends IService {
  override def execute(df : DataFrame, uid  : Long): DataFrame = {
    val rand = new scala.util.Random
    val UUID : String = s"${uid}-${rand.nextInt()}"

    val updateDF = df.withColumn("idClient", when(col("idClient") === uid, uid).otherwise(col("idClient")))
      .withColumn("FirstName", when(col("idClient") === uid, UUID).otherwise(col("FirstName")))
      .withColumn("LastName", when(col("idClient") === uid, UUID).otherwise(col("LastName")))
      .withColumn("Address", when(col("idClient") === uid, UUID).otherwise(col("Address")))

    updateDF
  }

}
