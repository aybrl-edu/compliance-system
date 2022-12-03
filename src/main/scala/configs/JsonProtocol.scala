package configs

import spray.json.{DefaultJsonProtocol, JsonFormat, RootJsonFormat}


object JsonProtocol extends DefaultJsonProtocol {
  implicit val configElement = jsonFormat2(ConfigElement.apply)
  implicit def dataFileConfig[ConfigElement : JsonFormat]: RootJsonFormat[DataFileConfig[ConfigElement]] = jsonFormat1(DataFileConfig.apply[ConfigElement])

}
