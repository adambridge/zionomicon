package exercises.ch01

import zio.ZIO

object Ch01Exercise01 {
  // 1. Implement a ZIO version of the function readFile by using the ZIO.attempt
  // constructor.
  def readFile(file: String): String = {
    val source = scala.io.Source.fromFile(file)

    try source.getLines().mkString
    finally source.close()
  }

  def readFileZio(file: String) = ZIO.attempt(readFile(file))
}
