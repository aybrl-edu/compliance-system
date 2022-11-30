import helpers.Helper

import scala.util.{Success, Failure}

object Main {
  def main(args: Array[String]): Unit = {
    Helper.parseCommand(args) match {
      case Success(command) => {
        println("Processing your command...")

      }
      case Failure(exception) => {}
        println("Malformed command!")
    }
  }
}
