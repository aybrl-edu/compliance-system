package configs

case class ArgConfig(uid: Long = -1,
                     action: String = "",
                     hdfsIP: String = "192.168.1.2",
                     hdfsPath: String = "compliance_system",
                     filename: String = "UserDataSample.csv",
                     read: Boolean = false)


