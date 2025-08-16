package exercises

import zio.{ZIO, ZIOAppDefault}


object Ch01Exercise20 extends ZIOAppDefault {
  // 20. Using recursion, write a function that will continue evaluating the specified effect
  // until the specified user-defined function evaluates to true on the output of the effect.

  def doWhile[R, E, A](
    body: ZIO[R, E, A]
  )(condition: A => Boolean): ZIO[R, E, A] =
    for {
      result <- body
      validResult <- if (condition(result)) ZIO.succeed(result) else doWhile(body)(condition)
    } yield validResult

  def body1 = ZIO.succeed(1) // Succeeds
  def body2 = ZIO.succeed(-1) // Runs forever
  def body3 = ZIO.fail(1) // Fails

  def condition(i: Int) = i > 0

  def run = doWhile(body3)(condition)
}

