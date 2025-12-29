package exercises.ch02.ex1

import zio.{Console, IO, ZIOAppDefault, durationInt}

import java.io.IOException

object Timer extends ZIOAppDefault {
  def countdown(from: Int): IO[IOException, Unit] = {
      if (from > 0 ) Console.printLine(s"$from").delay(1.seconds) *> countdown(from - 1)
      else Console.printLine(s"0")
  }

  def run: IO[IOException, Unit] = countdown(10)
}
