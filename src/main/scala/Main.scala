import helpers.Helper

import scala.util.{Success, Failure}

object Main {
  def main2(args: Array[String]): Unit = {
    // Parsing Command
    Helper.parseCommand(args) match {
      case Success(command) => {
        println("Processing your command...")

        // execute
        val hasExecuted = Orchestrator.executeCommand(command)

        // print result
        if(hasExecuted) println("Command has been successfully executed!")
        else println("Command was not executed! Halting...")
      }
      case Failure(exception) => {}
        println("Malformed command!")
    }
  }

  def main(args: Array[String]): Unit = {
    val scheme = Helper.sparkSchemeFromJSON()
    println(scheme)
  }
}
