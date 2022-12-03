import models.Command
import org.apache.spark.sql.DataFrame
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {
  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(command.getHDFSUrlFormatted) match {
      case Success(df: DataFrame) => {

        println("DF before command execution")
        df.show()
        println(s"${"-"*20}")

        if(command.isReadOnly) {
          return true
        }
        // Command execute
        val updatedDF = command.getService.execute(df, command.getUID)

        // Saving changes
        try {
          val hasWritten : Boolean = HDFSFileManager.writeCSVToHDFS(command.getHDFSUrlFormatted,
            command.getHDFSTempUrlFormatted,
            updatedDF)

          // log before
          println("DF after command execution")
          //updatedDF.show(20)
          println(s"${"-" * 20}")

          // Close
          if (!hasWritten) println("Exception while saving data to HDFS")
          hasWritten

        } catch {
          case _: Throwable =>
            println("Exception while saving data to HDFS")
            false
        }

      }
      case Failure(_) => {
        println("Exception while retrieving user data from HDFS")
        false
      }
    }
  }
}
