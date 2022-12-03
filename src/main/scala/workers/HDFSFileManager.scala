package workers

import models.UserInfo
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Try

/**
 * The aim of this class is to encapsulate the logic responsible for reading the data of a given csv file
 */
object HDFSFileManager {
  val sparkSession = SparkSession.builder().appName("compliance-system").getOrCreate()

  def readCSVFromHDFS(hdfsPath: String): Try[DataFrame] = {
    return null
  }

  def writeCSVToHDFS(hdfsPath: String, userData : List[UserInfo]): Boolean = {
    return false
  }
}
