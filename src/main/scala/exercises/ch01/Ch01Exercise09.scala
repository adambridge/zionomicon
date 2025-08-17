package exercises.ch01


object Ch01Exercise09 extends App {
  //  9. Implement the orElse function in terms of the toy model of a ZIO effect. The
  //  function should return an effect that tries the left-hand side, but if that effect fails,
  //  it will fall back to the effect on the right-hand side.

  def orElse[R, E1, E2, A](
    self: ZIO[R, E1, A],
    that: ZIO[R, E2, A]
  ): ZIO[R, E2, A] = {
    ZIO( r =>
      self.run(r) match {
        case Right(a) => Right(a)
        case Left(e1) => that.run(r)
      }
    )
  }

  val z1 = ZIO(incrementUnlessNegative)
  val z2 = ZIO(doubleUnlessOdd)
  val zelse = orElse(z1, z2)
  val result = zelse.run(-2)
  println(s"result: $result")
}
