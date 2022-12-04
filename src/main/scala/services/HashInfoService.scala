package services

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.functions.{col, when}



class HashInfoService extends IService {
  override def execute(df : DataFrame, uid  : Long): DataFrame = {

    /*def uuid = java.util.UUID.randomUUID.toString

    df2 = df("Firstname",df.Firstname.uuid)
    df3 = df.withColumn("Lastname",df.Lastname.randomUUID().toString)
    df4 = df.withColumn("Address",df.Address.randomUUID().toString)

      //randomUUID().toString*/
    val rand = new scala.util.Random
    val UUID : String = s"${uid}-${rand.nextInt()}"

    val updateDF = df.withColumn("idClient", when(col("idClient") === uid, uid))
      .withColumn("FirstName", when(col("idClient") === uid, UUID).otherwise(col("FirstName")))
      .withColumn("LastName", when(col("idClient") === uid, UUID).otherwise(col("LastName")))
      .withColumn("Address", when(col("idClient") === uid, UUID).otherwise(col("Address")))

    updateDF
  }

}
