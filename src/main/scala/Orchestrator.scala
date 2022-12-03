import helpers.Helper
import models.Command
import org.apache.spark.sql.DataFrame
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {
  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(command.getHDFSUrlFormatted) match {
      case Success(dataFrame: DataFrame) => {
        val fDataFrame = dataFrame.filter(s"IdClient < 1000")
        fDataFrame.show(10)
        Helper.dataFrameToCustom(dataFrame) match {
          case Success(usersData) => {
            val updatedUsersInfo = command.getService.execute(usersData)
            HDFSFileManager.writeCSVToHDFS(command.getHDFSUrlFormatted, updatedUsersInfo)
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
