package services

import models.UserInfo

trait IService {
  def execute(userData : List[UserInfo]): List[UserInfo]
}

