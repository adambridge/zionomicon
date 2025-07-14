package exercises

import zio.{ZIO, ZIOAppDefault}

object Ch01Exercise12 extends ZIOAppDefault {
  // 12. Using ZIO.fail and ZIO.succeed, implement the following function, which converts
  // a List into a ZIO effect by looking at the head element in the list and ignoring
  // the rest of the elements.

  def listToZIO[A](list: List[A]): ZIO[Any, None.type, A] =
    if (list.isEmpty) ZIO.fail(None)
    else ZIO.succeed(list.head)

  val run = {
    for {
      result <- listToZIO(List(0, 1, 2, 3))
    } yield println(s"result: $result")
  }
}

