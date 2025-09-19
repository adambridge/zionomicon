package exercises.ch02

import exercises.ch02.ex1.Timer
import zio.{ZIO, durationInt}
import zio.test.Assertion.equalTo
import zio.test.{TestClock, TestConsole, ZIOSpecDefault, assert, assertCompletes}

object TimerSpec extends ZIOSpecDefault {
  def spec = suite("TimerSpec")(
    test("countdown prints 5 second countdown (grug brained test)") {
      for {
        fiber <- Timer.countdown.fork
        _ <- TestClock.adjust(1.second)
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
        _ <- fiber.join
      } yield {
        assertCompletes
        assert(one)(equalTo(Vector("1\n")))
        assert(two)(equalTo(Vector("1\n", "2\n")))
        assert(three)(equalTo(Vector("1\n", "2\n", "3\n")))
        assert(four)(equalTo(Vector("1\n", "2\n", "3\n", "4\n")))
        assert(five)(equalTo(Vector("1\n", "2\n", "3\n", "4\n", "5\n")))
      }
    },

    test("countdown prints 5 second countdown (big brain version)") {
      for {
        fiber <- Timer.countdown.fork
        outputs <- ZIO.foreach(1 to 5) { _ =>
          TestClock.adjust(1.second) *> TestConsole.output
        } // Spurious error "No implicits found for parameter bf: zio.BuildFrom ..." ?
        _ <- TestClock.adjust(1.second)
        _ <- fiber.join
      } yield {
        assertCompletes
        assert(outputs.zipWithIndex.forall { case (out, i) =>
          out == (1 to (i + 1)).map(n => s"$n\n").toVector
        })(equalTo(true))
      }

    }
  )
}
