package samples.ch02

import zio.{ZIO, Console, durationInt}

package object s3 {
  val greet: ZIO[Any, Nothing, Unit] =
    for {
      name <- Console.readLine.orDie
      _ <- Console.printLine(s"Hello, $name!").orDie
    } yield ()

  val goShopping: ZIO[Any, Nothing, Unit] =
    Console.printLine("Going shopping!").orDie.delay(1.hour)
}
