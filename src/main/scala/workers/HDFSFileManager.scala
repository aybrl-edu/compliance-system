package workers

import helpers.Helper
import models.UserInfo
import org.apache.spark.sql.{DataFrame, SparkSession}
import scala.util.{Failure, Success, Try}

/**
 * The aim of this class is to encapsulate the logic responsible for reading/writing the data of a given csv file
 */
object HDFSFileManager {
  val sparkHost : String = "local"

  // Spark Session
  val sparkSession: SparkSession = SparkSession
    .builder
    .master(sparkHost)
    .appName("compliance-system")
    .enableHiveSupport()
    .getOrCreate()

  def readCSVFromHDFS(hdfsPath: String): Try[DataFrame] = {
    println(s"${"-" * 25} READING FILE STARTED ${"-" * 25}")

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
      case _: Throwable =>
        sparkSession.close()
        Failure(new Throwable("cannot read file from HDFS"))
    }
  }

  def writeCSVToHDFS(hdfsPath: String, df : DataFrame): Boolean = {
    println(s"${"-" * 25} SAVING FILE STARTED ${"-" * 25}")
    df.write
      .format("csv")
      .option("header", "true")
      .save(hdfsPath)
    false
  }
}