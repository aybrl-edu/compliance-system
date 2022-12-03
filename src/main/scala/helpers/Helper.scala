package helpers

import configs.{ArgConfig, ConfigElement, DataFileConfig}
import models.{Command, UserInfo}
import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.types.{BooleanType, DataType, DateType, DoubleType, FloatType, IntegerType, LongType, StringType, StructField, StructType}
import scopt.{OParser, OParserBuilder}
import spray.json._

import scala.util.{Failure, Success, Try}


object Helper {
  // Scopt Parser
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

  // Spray Json


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

  def sparkSchemeFromJSON(): StructType = {
    // Basic type mapping map
    val stringToType = Map[String, DataType](
      "String" -> StringType,
      "Double" -> DoubleType,
      "Float" -> FloatType,
      "Int" -> IntegerType,
      "Boolean" -> BooleanType,
      "Long" -> LongType,
      "DateTime" -> DateType
    )

    // Load JSON From file
    val source = scala.io.Source.fromFile("src/config/DataFileConfig.json")
    val lines = try source.mkString finally source.close()
    val json = lines.parseJson

    // Parse to case class
    import configs.JsonProtocol._
    val datafileConfig = json.convertTo[DataFileConfig[ConfigElement]]

    // Convert case class to StructType
    var structSeq: Seq[StructField] = Seq()
    datafileConfig.columns.foreach(configElement => {
      structSeq = structSeq :+ StructField(configElement.name, stringToType(configElement.typeOf), nullable = false)
    })
    val schema = StructType(structSeq)
    schema
  }
}


