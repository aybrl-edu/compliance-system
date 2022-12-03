package services

import models.UserInfo
import org.apache.spark.sql.DataFrame

class DeleteInfoService extends IService {
  override def execute(df: DataFrame, uid: Long): DataFrame = {
    df.filter(s"idClient != ${uid}")
  }
}
