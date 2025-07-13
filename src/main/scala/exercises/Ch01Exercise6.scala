package exercises


object Ch01Exercise6 extends App {
  // 6. Implement the zipWith function in terms of the toy model of a ZIO effect. The
  // function should return an effect that sequentially composes the specified effects,
  // merging their results with the specified user-defined function.

  final case class ZIO[-R, +E, +A](run: R => Either[E, A])

  def zipWith[R, E, A, B, C](
    self: ZIO[R, E, A],
    that: ZIO[R, E, B]
  )(f: (A, B) => C): ZIO[R, E, C] = {
    ZIO(r =>
      for {
        a <- self.run(r)
        b <- that.run(r)
      } yield f(a, b)
    )
  }

  def incrementUnlessNegative(x: Int) =
    if (x >= 0) Right(x + 1)
    else Left("Nope")

  def doubleUnlessOdd(x: Int) =
    if (x % 2 == 0) Right(2 * x)
    else Left("Nein danke")

  val z1 = ZIO(incrementUnlessNegative)
  val z2 = ZIO(doubleUnlessOdd)
  val z3 = zipWith(z1, z2)(_ + _)
  val result = z3.run(2)
  println(f"result: $result")
}
