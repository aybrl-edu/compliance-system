import models.Command
import org.apache.spark.sql.DataFrame
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {
  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(command.getHDFSUrlFormatted) match {
      case Success(df: DataFrame) =>
        df.show()

        if(command.isReadOnly) return true

        // Command execute
        val updatedDF = command.getService.execute(df, command.getUID)

        try {
          // Saving changes
          val hasWritten : Boolean = HDFSFileManager.writeCSVToHDFS(command.getHDFSUrlFormatted, updatedDF)
          if (!hasWritten) println("Exception while saving data to HDFS")
          hasWritten

        } catch {
          case _: Throwable =>
            println("Exception while saving data to HDFS")
            false
        }
      case Failure(_) => {
        println("Exception while retrieving user data from HDFS")
        false
      }
    }
  }
}
