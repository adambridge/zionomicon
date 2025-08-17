package exercises.ch01

import exercises.Ch01Exercise11.eitherToZIO
import zio.{ZIO, ZIOAppDefault}

object Ch01Exercise11 extends ZIOAppDefault {
  // 11. Using ZIO.fail and ZIO.succeed, implement the following function, which
  // converts an Either into a ZIO effect:

  def eitherToZIO[E, A](either: Either[E, A]): ZIO[Any, E, A] =
    either match {
      case Left(e) => ZIO.fail(e)
      case Right(a) => ZIO.succeed(a)
    }

  val run = {
    for {
      result <- eitherToZIO(incrementUnlessNegative(3))
    } yield println(s"result: $result")
  }
}

