package samples.ch01.s2

import zio.{ZIO, ZIOAppDefault}

import scala.io.StdIn


object Ch01S02Sample002 extends ZIOAppDefault {
  val readline =
    ZIO.attempt(StdIn.readLine())

  def printline(line: String) =
    ZIO.attempt(println(line))

  val echofm =
    readline.flatMap(line => printline(line))

  val echo =
    for {
      line <- readline
      _ <- printline(line)
    } yield ()

  val run = echo
}