import models.Command
import org.apache.spark.sql.DataFrame
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {
  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(command.getHDFSUrlFormatted) match {
      case Success(df: DataFrame) => {

        println("DF before command execution")
        df.show(20)
        println(s"${"-"*20}")

        // Command execute
        val updatedDF = command.getService.execute(df, command.getUID)

        // Saving changes
        //val hasWritten : Boolean = HDFSFileManager.writeCSVToHDFS(command.getHDFSUrlFormatted, updatedDF)

        // log before
        println("DF after command execution")
        updatedDF.show(20)
        println(s"${"-" * 20}")

        // Verification
        if(!true) println("Exception while saving data to HDFS")
        true
      }
      case Failure(exception) => {
        println("Exception while retrieving user data from HDFS")
        false
      }
    }
  }
}
