package services

import models.UserInfo
import org.apache.spark.sql.DataFrame

trait IService {
  def execute(df : DataFrame, uid : Long): DataFrame
}
