package samples.ch01.s2

import zio.{ZIO, ZIOAppDefault}

import scala.io.StdIn

object Ch01S02Sample001a extends ZIOAppDefault {
  val readline =
    ZIO.attempt(StdIn.readLine())

  def printline(line: String) =
    ZIO.attempt(println(line))

  val echo =
    readline.flatMap(line => printline(line))

  val run = echo
}

object Ch01S02Sample001b extends App {
  val line = StdIn.readLine()
  println(line)
}
