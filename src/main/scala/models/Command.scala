package models

import services.{DeleteInfoService, HashInfoService, IService}

class Command {
  var service : IService = null
  var uid : Long = null


  def getUID : Long = {
    uid
  }

  def setUID(newUID : Long) : Unit = {
    uid = newUID
  }
  def getService: IService = {
    service
  }
  def setService(newService: IService): Unit = {
    service = newService
  }

  def setService(cmd: String): Unit = {
    // @todo : replace w/ switch and custom enum for services
    if(cmd == "delete") service = new DeleteInfoService()
    else if(cmd == "hash") service = new HashInfoService()
  }
}
