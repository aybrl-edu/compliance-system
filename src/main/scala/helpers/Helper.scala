package helpers

import models.{Command, UserInfo}
import org.apache.spark.sql.DataFrame

import scala.util.{Failure, Success, Try}


object Helper {
  // @todo
  def parseCommand(command: Array[String]): Try[Command] = {
    val cmd = new Command()
    null
  }

  // @todo
  def dataFrameToCustom(data : DataFrame) : Try[List[UserInfo]] = {
    null
  }
}
