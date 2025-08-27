package exercises.ch01

import exercises.ch01.Ch01Exercise01.readFileZio
import zio.ZIO.foreach
import zio.ZIOAppDefault


object Ch01Exercise10 extends ZIOAppDefault {
  // 10. Using the following code as a foundation, write a ZIO application that prints out the
  // contents of whatever files are passed into the program as command-line arguments.
  // You should use the function readFileZio that you developed in these exercises, as
  // well as ZIO.foreach.

  def run =
    for {
      args <- getArgs
      lines <- foreach(args)(readFileZio)
    } yield println(lines)
}
