package exercises


object Ch01Exercise8 extends App {
  //  8. Implement the foreach function in terms of the toy model of a ZIO effect. The
  //  function should return an effect that sequentially runs the specified function on
  //  every element of the specified collection.

  def foreach[R, E, A, B](
    in: Iterable[A]
  )(f: A => ZIO[R, E, B]): ZIO[R, E, List[B]] =
    ???

}
