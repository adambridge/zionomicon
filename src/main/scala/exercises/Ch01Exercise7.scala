package exercises

import exercises.Ch01Exercise6.zipWith


object Ch01Exercise7 extends App {
  //  7. Implement the collectAll function in terms of the toy model of a ZIO effect. The
  //  function should return an effect that sequentially collects the results of the specified
  //  collection of effects.

  def collectAll[R, E, A](
    in: Iterable[ZIO[R, E, A]]
  ): ZIO[R, E, List[A]] = {
    if (in.isEmpty) ZIO(r => Right(List.empty[A]))
    else zipWith(
      in.head,
      collectAll(in.tail)
    )((a: A, as: List[A]) => a :: as)
  }

  val z1 = ZIO(incrementUnlessNegative)
  val z2 = ZIO(doubleUnlessOdd)
  val zs = List(z1, z2)
  val collectzs = collectAll(zs)
  val collected = collectzs.run(4)
  println(s"collected: $collected")

}
