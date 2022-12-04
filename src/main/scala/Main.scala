import helpers.Helper

import scala.util.{Success, Failure}

object Main {
  def main(args: Array[String]): Unit = {
    // Parsing Command
    Helper.parseCommand(args) match {
      case Success(command) => {
        println("\nProcessing your command... \n")

        // execute
        val hasExecuted = Orchestrator.executeCommand(command)

        // print result
        if(hasExecuted) println("\ncommand has been successfully executed! \n")
        else println("\ncommand was not executed! Halting... \n")
      }
      case Failure(exception) =>
        println(s"\ncommand malformed error => ${exception.getMessage} \n")
    }
  }
}
