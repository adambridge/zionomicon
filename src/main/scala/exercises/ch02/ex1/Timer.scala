package exercises.ch02.ex1

import zio.{Console, ZIO, ZIOAppDefault, durationInt}

import java.time.LocalDateTime

object Timer extends ZIOAppDefault {
  def countdown =
    ZIO.foreach(1 to 5) { n =>
      Console.printLine(s"$n").delay(1.seconds)
    } // Spurious error "No implicits found for parameter bf: zio.BuildFrom ..." ?


  def run = countdown
}
