import helpers.Helper

import scala.util.{Success, Failure}

object Main {
  def main(args: Array[String]): Unit = {
    // Parsing Command
    Helper.parseCommand(args) match {
      case Success(command) => {
        println("\n Processing your command... \n")

        // execute
        val hasExecuted = Orchestrator.executeCommand(command)

        // print result
        if(hasExecuted) println("\n command has been successfully executed! \n")
        else println("\n command was not executed! Halting... \n")
      }
      case Failure(exception) =>
        println(s"\n command malformed error => ${exception.getMessage} \n")
    }
  }
}
