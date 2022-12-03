package helpers

import configs.ArgConfig
import models.{Command, UserInfo}
import org.apache.spark.sql.DataFrame
import scopt.{OParser, OParserBuilder}

import scala.util.{Failure, Success, Try}


object Helper {
  val builder: OParserBuilder[ArgConfig] = OParser.builder[ArgConfig]

  val parser: OParser[Unit, ArgConfig] = {
    import builder._
    OParser.sequence(
      programName("compliance-system"),
      head("compliance-system", "1.0"),
      opt[Long]('u', "uid")
        .required()
        .valueName("<uid>")
        .action((x, c) => c.copy(uid = x))
        .text("user id is required"),

      opt[String]('a', "action")
        .required()
        .valueName("<action> <- ['delete', 'hash']")
        .action((x, c) => c.copy(action = x))
        .text("action is required"),
      checkConfig(c => {
        val allowedActions = List("delete", "hash")
        if (!allowedActions.contains(c.action)) failure("action must be in ['delete', 'hash']")
        else success
      })
    )
  }
  def parseCommand(args: Array[String]): Try[Command] = {
    val command = new Command()

    OParser.parse(parser, args, ArgConfig()) match {
      case Some(config) =>
        command.setService(config.action)
        command.setUID(config.uid)
        Success(command)
      case _ =>
        Failure(new Throwable("program args invalid"))
    }
  }

  // @todo
  def dataFrameToCustom(data : DataFrame) : Try[List[UserInfo]] = {
    null
  }
}
