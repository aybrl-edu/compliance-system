package services

import models.UserDataInfo
import org.apache.spark.sql.Dataset

class DeleteInfoService extends IService {
  override def execute(ds: Dataset[UserDataInfo], uid: Long): Dataset[UserDataInfo] = {
    ds.filter(row => row.idClient != uid)
  }
}
