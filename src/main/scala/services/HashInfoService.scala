package services

import org.apache.spark.sql.DataFrame
import io.jvm.uuid._
import java.util.UUID


class HashInfoService extends IService {
  override def execute(df : DataFrame, uid  : Long): DataFrame = {

    def uuid = java.util.UUID.randomUUID.toString

    df2 = df("Firstname",df.Firstname.uuid)
    df3 = df.withColumn("Lastname",df.Lastname.randomUUID().toString)
    df4 = df.withColumn("Address",df.Address.randomUUID().toString)

      //randomUUID().toString
  }

}
