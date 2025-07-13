package exercises

import exercises.Ch01Exercise6.ZIO

import scala.annotation.tailrec


object Ch01Exercise7 extends App {
  //  7. Implement the collectAll function in terms of the toy model of a ZIO effect. The
  //  function should return an effect that sequentially collects the results of the specified
  //  collection of effects.

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

  def collectAll[R, E, A](
    in: Iterable[ZIO[R, E, A]]
  ): ZIO[R, E, List[A]] = {
    if (in.isEmpty) ZIO(r => Right(List.empty[A]))
    else zipWith(
      in.head,
      collectAll(in.tail)
    )((a: A, as: List[A]) => a :: as)
  }

  def incrementUnlessNegative(x: Int) =
    if (x >= 0) Right(x + 1)
    else Left("Nope")

  def doubleUnlessOdd(x: Int) =
    if (x % 2 == 0) Right(2 * x)
    else Left("Nein danke")

  val z1 = ZIO(incrementUnlessNegative)
  val z2 = ZIO(doubleUnlessOdd)
  val zs = List(z1, z2)
  val collectzs = collectAll(zs)
  val collected = collectzs.run(4)
  println(s"collected: $collected")

}
