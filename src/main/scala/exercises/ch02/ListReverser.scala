package exercises.ch02

import zio.{Console, Task, ZIO, ZIOAppDefault}

import scala.annotation.tailrec

object ListReverser {
  def reverse[T](l: List[T]): Task[List[T]] =
    ZIO.attempt(accumulateReverse(l, List[T]()))

  @tailrec
  private def accumulateReverse[T](l: List[T], acc: List[T]) :List[T] = {
    l match {
      case Nil => acc
      case h :: t => accumulateReverse(t, h :: acc)
    }
  }
}

object ListReverserApp extends ZIOAppDefault {
  def run: ZIO[Any, Throwable, Unit] = for {
    reversed <- ListReverser.reverse(List("a", "l", "o", "h"))
    _ <- Console.printLine(reversed)
  } yield ()
}
