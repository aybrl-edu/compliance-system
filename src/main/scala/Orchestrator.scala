import helpers.Helper
import models.Command
import org.apache.spark.sql.{DataFrame, Dataset}
import workers.HDFSFileManager

import scala.util.{Failure, Success}

object Orchestrator {
  def executeCommand(command : Command) : Boolean = {
    HDFSFileManager.readCSVFromHDFS(command.getHDFSUrlFormatted, command.getFileType) match {
      case Success(df: DataFrame) =>
        df.show()

        // Transform df to ds
        val ds = Helper.dataframeToDataset(df)

        if(command.isReadOnly) return true

        // Command execute
        val updatedDS = command.getService.execute(ds, command.getUID)

        try {
          // Saving changes
          val hasWritten : Boolean = HDFSFileManager.writeCSVToHDFS(command.getHDFSUrlFormatted,
            command.getFileType,
            updatedDS)

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
