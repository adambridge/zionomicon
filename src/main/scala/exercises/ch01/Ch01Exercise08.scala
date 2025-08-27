package exercises.ch01

import exercises.ZIO
import exercises.ch01.Ch01Exercise07.collectAll


object Ch01Exercise08 extends App {
  //  8. Implement the foreach function in terms of the toy model of a ZIO effect. The
  //  function should return an effect that sequentially runs the specified function on
  //  every element of the specified collection.

  def foreach[R, E, A, B](
    in: Iterable[A]
  )(f: A => ZIO[R, E, B]): ZIO[R, E, List[B]] =
    collectAll(in.map(f))

  def addIfEven(a: Int, b: Int): Either[String, Int] =
    if (b % 2 != 0) Left("Bad boy!")
    else Right(a + b)

  val is: List[Int] = List(1, 2, 3)
  val eachz: ZIO[Int, String, List[Int]] = foreach(is)(i => ZIO(r => addIfEven(i, r)))
  val result = eachz.run(2)
  println(s"result: $result")
}
