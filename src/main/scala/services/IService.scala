package services

import models.UserDataInfo
import org.apache.spark.sql.Dataset

trait IService {
  def execute(df : Dataset[UserDataInfo], uid : Long): Dataset[UserDataInfo]
}

