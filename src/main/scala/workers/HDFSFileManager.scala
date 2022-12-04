package workers

import helpers.Helper
import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

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
    // Log
    println(s"${"-" * 25} READING FILE STARTED ${"-" * 25}")
    println(s"${" " * 25} reading from ${hdfsPath} ${" " * 25}")

    // Spark-session context
    sparkSession.sparkContext.setCheckpointDir("tmp")
    sparkSession.sparkContext.setLogLevel("ERROR")

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
        Failure(new Throwable("cannot read file from HDFS"))
    }
  }

  def writeCSVToHDFS(hdfsPath: String, hdfsTempPath : String, df : DataFrame): Boolean = {
    println(s"${"-" * 25} SAVING FILE STARTED ${"-" * 25}")

    sparkSession.sparkContext.setLogLevel("ERROR")

    try {
      // Write to final
      df.checkpoint(true)
        .write
        .format("csv")
        .mode(SaveMode.Overwrite)
        .save(hdfsPath)
      true
    }
    catch {
      case _: Throwable =>
        println("error while saving data")
        false
    }
  }
}
