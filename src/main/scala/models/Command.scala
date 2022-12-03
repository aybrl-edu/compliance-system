package models

import services.{DeleteInfoService, HashInfoService, IService}

class Command {
  var service : IService = null
  var uid : Long = 0
  var hdfsIP : String = "172.31.252.170"
  var hdfsFilePath : String = "compliance_system/datacsv"
  var fileName : String = "UserDataSample.csv"


  def getUID : Long = {
    uid
  }

  def getService: IService = {
    service
  }
  def setService(newService: IService): Unit = {
    service = newService
  }

  def setUID(newUID: Long): Unit = {
    uid = newUID
  }

  def setHDFS_IP(newHdfsIP: String): Unit = {
    hdfsIP = newHdfsIP
  }

  def setHdfsFilePath(newHdfsFilePath: String): Unit = {
    hdfsFilePath = newHdfsFilePath
  }

  def setFileName(newFileName: String): Unit = {
    fileName = newFileName
  }

  def setService(cmd: String): Unit = {
    // @todo : replace w/ switch and custom enum for services
    if(cmd == "delete") service = new DeleteInfoService()
    else if(cmd == "hash") service = new HashInfoService()
  }

  def getHDFSUrlFormatted : String = {
    String.format("hdfs://%s:9000/%s/", hdfsIP, hdfsFilePath)
  }

  def getHDFSTempUrlFormatted: String = {
    String.format("hdfs://%s:9000/%s/temp", hdfsIP)
  }
}
