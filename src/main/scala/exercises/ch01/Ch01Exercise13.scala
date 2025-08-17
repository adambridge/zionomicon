package exercises.ch01

import zio.{ZIO, ZIOAppDefault}

object Ch01Exercise13 extends ZIOAppDefault {
  //  13. Using ZIO.succeed, convert the following procedural function into a ZIO function:
  def currentTime(): Long = java.lang.System.currentTimeMillis()

  lazy val currentTimeZIO: ZIO[Any, Nothing, Long] =
    ZIO.succeed(currentTime())

  val run = {
    for {
      result <- currentTimeZIO
    } yield println(s"result: $result")
  }
}

