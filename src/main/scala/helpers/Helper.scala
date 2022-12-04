package helpers

import configs.{ArgConfig, ConfigElement, DataFileConfig}
import models.Command
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
        .valueName("<uid>")
        .action((x, c) => c.copy(uid = x))
        .text("user id is required if not read"),

      opt[String]('a', "action")
        .valueName("<delete> | <hash>")
        .action((x, c) => c.copy(action = x))
        .text("action is required if not read"),

      opt[String]('i', "hdfsIP")
        .valueName("<hdfsIP>")
        .action((x, c) => c.copy(hdfsIP = x))
        .text("custom hdfs ip, default : 192.168.1.2"),

      opt[String]('p', "hdfsPath")
        .valueName("<hdfsPath>")
        .action((x, c) => c.copy(hdfsPath = x))
        .text("custom data path, default : compliance_system"),

      opt[String]('f', "filename")
        .valueName("<filename>")
        .action((x, c) => c.copy(filename = x))
        .text("custom filename, default : UserDataSample.csv"),

      opt[Boolean]('r', "read")
        .valueName("<read>")
        .action((x, c) => c.copy(read = x))
        .text("read data"),

      opt[String]('t', "fileType")
        .valueName("<fileType>")
        .action((x, c) => c.copy(fileType = x))
        .text("file type -> CSV (Default) | PARQUET"),

      checkConfig(c => {
        val allowedActions = List("delete", "hash")
        val allowedTypes = List("csv", "parquet")

        if(!c.read && (c.uid == -1 || c.action == "")) failure("no command or action was specified")
        else if (c.action != "" && !allowedActions.contains(c.action)) failure("action must be in ['delete', 'hash']")
        else if (!allowedTypes.contains(c.fileType)) failure("type must be in ['csv', 'parquet']")
        else success
      })
    )
  }

  def parseCommand(args: Array[String]): Try[Command] = {
    val command = new Command()

    OParser.parse(parser, args, ArgConfig()) match {
      case Some(config) =>
        if(!config.read && (config.uid == -1 || config.action == "")) {
          return Failure(new Throwable("program args invalid: no command was specified"))
        }
        command.setService(config.action)
        command.setUID(config.uid)
        command.setReadOnly(config.read)
        command.setHDFS_IP(config.hdfsIP)
        command.setHdfsFilePath(config.hdfsPath)
        command.setFileName(config.filename)
        Success(command)
      case _ =>
        Failure(new Throwable("program args invalid"))
    }
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
    StructType(structSeq)
  }
}


