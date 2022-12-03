package services

import org.apache.spark.sql.DataFrame

class DeleteInfoService extends IService {
  override def execute(df: DataFrame, uid: Long): DataFrame = {
    df.filter(df("idClient") =!= uid)
  }
}
