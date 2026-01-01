package exercises.ch02

import exercises.ch02.ex1.Timer
import zio.{ZIO, durationInt}
import zio.test.Assertion.equalTo
import zio.test.{Spec, TestClock, TestConsole, ZIOSpecDefault, assert, assertCompletes}

import java.io.IOException

object TimerSpec extends ZIOSpecDefault {
  def spec: Spec[Any, IOException] = suite("TimerSpec")(
    test("countdown prints 5 second countdown (grug brained test)") {
      for {
        fiber <- Timer.countdown(5).fork
        _ <- TestClock.adjust(1100.millis)
        one <- TestConsole.output
        _ <- TestClock.adjust(1.second)
        two <- TestConsole.output
        _ <- TestClock.adjust(1.second)
        three <- TestConsole.output
        _ <- TestClock.adjust(1.second)
        four <- TestConsole.output
        _ <- TestClock.adjust(1.second)
        five <- TestConsole.output
        _ <- TestClock.adjust(1.second)
        six <- TestConsole.output
        _ <- fiber.join
      } yield {
        assertCompletes &&
        assert(one)(equalTo(Vector("5\n"))) &&
        assert(two)(equalTo(Vector("5\n", "4\n"))) &&
        assert(three)(equalTo(Vector("5\n", "4\n", "3\n"))) &&
        assert(four)(equalTo(Vector("5\n", "4\n", "3\n", "2\n"))) &&
        assert(five)(equalTo(Vector("5\n", "4\n", "3\n", "2\n", "1\n"))) &&
        assert(six)(equalTo(Vector("5\n", "4\n", "3\n", "2\n", "1\n", "0\n")))
      }
    },
    test("countdown prints 5 second countdown (big brain version)") {
      for {
        fiber <- Timer.countdown(5).fork
        outputs <- ZIO.collectAll(
          List.fill(6)(TestConsole.output <* TestClock.adjust(1.second))
        )
        _ <- fiber.join
      } yield {
        assertCompletes &&
          outputs.zipWithIndex.map { case (out, i) =>
            val expected = (5 until 5 - i by -1).map(n => s"$n\n").toVector
            assert(out)(equalTo(expected))
          }.reduce(_ && _)
      }
    }
  )
}
