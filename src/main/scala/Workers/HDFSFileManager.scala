package Workers

import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.Try

/**
 * The aim of this class is to encapsulate the logic responsible for reading the data of a given csv file
 */
class HDFSFileManager {
  val sparkSession = SparkSession.builder().appName("compliance-system").getOrCreate()

  def readCSVFromHDFS(hdfsPath: String): Try[DataFrame] = {
    return null
  }
}
