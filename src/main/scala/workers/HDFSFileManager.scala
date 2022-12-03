package workers

import helpers.Helper
import models.UserInfo
import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.util.control.Exception
import scala.util.{Failure, Success, Try}

/**
 * The aim of this class is to encapsulate the logic responsible for reading the data of a given csv file
 */
object HDFSFileManager {
  val sparkHost : String = "local"

  // Spark Session
  val sparkSession = SparkSession
    .builder
    .master(sparkHost)
    .appName("compliance-system")
    .enableHiveSupport()
    .getOrCreate()

  def readCSVFromHDFS(hdfsPath: String): Try[DataFrame] = {
    // Load data from HDFS
    try{
      val df = sparkSession.read
        .schema(Helper.sparkSchemeFromJSON())
        .format("csv")
        .option("header", "true")
        .option("mode", "DROPMALFORMED")
        .load(hdfsPath)

      Success(df)
    } catch {
      case _: Throwable => Failure(new Throwable("cannot read file from HDFS"))
    }
  }

  def writeCSVToHDFS(hdfsPath: String, userData : List[UserInfo]): Boolean = {
    return false
  }
}
