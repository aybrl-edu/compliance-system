package configs

case class ArgConfig(uid: Long = 1,
                     action: String = "delete",
                     hdfsIP: String = "172.10.253.170",
                     hdfsPath: String = "compliance_system",
                     filename: String = "UserDataSample.csv")


