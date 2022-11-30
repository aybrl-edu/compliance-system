package models

import services.{DeleteInfoService, HashInfoService, IService}

class Command {
  var service : IService = null

  def setService(newService: IService): Unit = {
    service = newService
  }

  def setService(cmd: String): Unit = {
    // @todo : replace w/ switch and custom enum for services
    if(cmd == "delete") service = new DeleteInfoService()
    else if(cmd == "hash") service = new HashInfoService()
  }
  def getService: IService = {
    return service
  }
}
