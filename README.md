# compliance-system
GPDR Compliance System

Project : https://dark-cockroach-f61.notion.site/Examen-TP-ING3-444c3e5e00b74a06b14e408e89dbf1fd

## Process Design

### 1. Class Diagram

![image](https://user-images.githubusercontent.com/114408910/205469717-3765ee35-4bfd-40c6-a652-bbf98cbecd3e.png)

### 2. Sequence Diagram

![image](https://user-images.githubusercontent.com/114408910/205469734-5c35393b-c7dd-4e1e-9c01-bb764aeb134b.png)

## Usage Manual
```
compliance-system 1.0
Usage: compliance-system [options]

  -u, --uid       <uid>          User id. Required if not read
  -a, --action    <action>       Action. Required if not read
  -i, --hdfsIP    <hdfsIP>       Custom hdfs ip, default : 192.168.1.2
  -p, --hdfsPath  <hdfsPath>     Custom data path, default : compliance_system/datacsv
  -f, --filename  <filename>     Custom filename without extension
  -r, --read      <read>         Read data
  -t, --fileType  <fileType>     File type -> {CSV (Default) | PARQUET}
```


**Two Modes of usage:** reading and executing

It reads the data from a file or a folder. The execution of an action (delete, hash) is only possible on one uid at a time for now.

### Read data

``` sbt "run -r true [-i <hdfs_ip>] [-p <hdfs_path>] [-f <filename>] [-t <filetype>]" ```

### Execute an action

``` sbt "run -a <delete | hash> -u <long> [-i <hdfs_ip>] [-p <hdfs_path>] [-f <filename>] [-t <filetype>]" ```
  
## Explications

Below, a few explications about the configuration classes implemented

### ArgConfig

The argument parsed by scopt will be mapped as an object of this class. Here we define the default values for each argument.
``` 
case class ArgConfig(uid: Long = -1,
                     action: String = "",
                     hdfsIP: String = "192.168.1.2",
                     hdfsPath: String = "compliance_system/datacsv",
                     filename: String = "",
                     fileType: String = "csv",
                     read: Boolean = false)
```
### JsonProtocol

The Config JSON file parsed by spray-json will be mapped into an object of this class. This class is used to provide the format in which the json string should be processed
  
``` 
object JsonProtocol extends DefaultJsonProtocol {
  implicit val configElement: RootJsonFormat[ConfigElement] = jsonFormat2(ConfigElement.apply)
  implicit def dataFileConfig[ConfigElement : JsonFormat]: RootJsonFormat[DataFileConfig[ConfigElement]] = jsonFormat1(DataFileConfig.apply[ConfigElement])
}
```
### DataFileConfig
  
After parsing the JSON, the data will be mapped into an object of this DataFileConfig class. These classes will be used later to create a StructType Schema to validate the data when reading files from HDFS with Spark.
  
``` 
case class DataFileConfig[ConfigElement](columns : List[ConfigElement])

case class ConfigElement(name : String, typeOf : String)
```
