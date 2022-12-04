package models

import services.{DeleteInfoService, HashInfoService, IService}

class Command {
  var service : IService = null
  var uid : Long = 0
  var hdfsIP : String = ""
  var hdfsFilePath : String = ""
  var fileName : String = ""
  var fileType : String = "csv"
  var readOnly : Boolean = false


  def getUID : Long = {
    uid
  }

  def getFileType : String = {
    fileType
  }

  def getService: IService = {
    service
  }

  def isReadOnly: Boolean = {
    readOnly
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

  def setFileType (newFileType: String): Unit = {
    fileType = newFileType
  }

  def setService(cmd: String): Unit = {
    // @todo : replace w/ switch and custom enum for services
    if(cmd == "delete") service = new DeleteInfoService()
    else if(cmd == "hash") service = new HashInfoService()
  }

  def setReadOnly(ro : Boolean): Unit = {
    readOnly = ro
  }


  def getHDFSUrlFormatted : String = {
    var path = String.format("hdfs://%s:9000/%s/", hdfsIP, hdfsFilePath)
    if(fileName != "") path = path + s"${fileName}/.${fileType}"
    path
  }

}
