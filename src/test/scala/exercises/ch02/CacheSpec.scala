package exercises.ch02

import exercises.ch02.ex2.Cache
import zio.durationInt
import zio.test.Assertion.equalTo
import zio.test.{TestClock, ZIOSpecDefault, assert}

object CacheSpec extends ZIOSpecDefault {
  def spec = suite("CacheSpec")(
    test("store a value") {
      val cache = new Cache
      for {
        fiber <- cache.store("k", "v", 1000).fork
        _ <- TestClock.adjust(500.millis)
        _ <- fiber.join
        value <- cache.retrieve("k")
      } yield {
        assert(value)(equalTo(Some("v")))
      }
    },
    test("don't return expired value") {
      val cache = new Cache
      for {
        fiber <- cache.store("k", "v", 1000).fork
        _ <- TestClock.adjust(1500.millis)
        _ <- fiber.join
        value <- cache.retrieve("k")
      } yield {
        assert(value)(equalTo(None))
      }
    },
    test("cache multiple values") {
      val cache = new Cache
      for {
        _ <- cache.store("ka", "va", 1000)
        _ <- cache.store("kb", "vb", 2000)
        fiber <- zio.ZIO.unit.fork
        _ <- zio.ZIO.yieldNow
        _ <- TestClock.adjust(1500.millis)
        _ <- fiber.join
        valueA <- cache.retrieve("ka")
        valueB <- cache.retrieve("kb")
      } yield {
        assert(valueA)(equalTo(None))
        assert(valueB)(equalTo(Some("vb")))
      }
    },
  )
}
