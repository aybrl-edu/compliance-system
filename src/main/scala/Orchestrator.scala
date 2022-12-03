import helpers.Helper
import models.Command
import org.apache.spark.sql.DataFrame
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {

  val hdfsPath = ""

  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(hdfsPath) match {
      case Success(dataFrame: DataFrame) => {
        Helper.dataFrameToCustom(dataFrame) match {
          case Success(usersData) => {
            val updatedUsersInfo = command.getService.execute(usersData)
            HDFSFileManager.writeCSVToHDFS(hdfsPath, updatedUsersInfo)
          }
          case Failure(exception) => {
            println("Exception while processing user data")
            false
          }
        }
      }
      case Failure(exception) => {
        println("Exception while retrieving user data from HDFS")
        false
      }
    }
  }
}
