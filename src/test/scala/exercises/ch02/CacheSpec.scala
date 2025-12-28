package exercises.ch02

import exercises.ch02.ex2.Cache
import zio.durationInt
import zio.test.Assertion.equalTo
import zio.test.{TestClock, ZIOSpecDefault, assert}

object CacheSpec extends ZIOSpecDefault {
  def spec = suite("CacheSpec")(
    test("cache multiple values") {
      for {
        _ <- Cache.store("ka", "va", 1000)
        _ <- Cache.store("kb", "vb", 2000)
        fiber <- zio.ZIO.unit.fork
        _ <- zio.ZIO.yieldNow
        _ <- TestClock.adjust(1500.millis)
        _ <- fiber.join
        valueA <- Cache.retrieve("ka")
        valueB <- Cache.retrieve("kb")
      } yield {
        assert(valueA)(equalTo(None))
        assert(valueB)(equalTo(Some("vb")))
      }
    }
  )
}
