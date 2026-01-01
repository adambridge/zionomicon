package exercises.ch02

import exercises.ch02.ex3.RateLimiter
import zio.test.Assertion.equalTo
import zio.test.{Spec, TestClock, ZIOSpecDefault, assert, assertCompletes}
import zio.{ZIO, durationInt}

object RateLimiterSpec extends ZIOSpecDefault {
  def spec: Spec[Any, Throwable] = suite("RateLimiterSpec")(
    test("N executions are allowed") {
      val rateLimiter = new RateLimiter(5)
      for {
        results <- ZIO.collectAll(
          List.fill(5)(rateLimiter.execute)
        )
      } yield {
        assertCompletes &&
        results.map(r => assert(r)(equalTo(true))).reduce(_ && _)
      }
    },
    test("N + 1 th execution is not allowed") {
      val rateLimiter = new RateLimiter(5)
      for {
        results <- ZIO.collectAll(
          List.fill(5)(rateLimiter.execute)
        )
        nthResult <- rateLimiter.execute
      } yield {
        assertCompletes &&
          results.map(r => assert(r)(equalTo(true))).reduce(_ && _) &&
          assert(nthResult)(equalTo(false))
      }
    },
    test("Execution succeeds after waiting a minute") {
      val rateLimiter = new RateLimiter(5)
      for {
        results <- ZIO.collectAll(
          List.fill(5)(rateLimiter.execute)
        )
        shouldFail <- rateLimiter.execute
        _ <- TestClock.adjust(60.seconds)
        shouldSucceed <- rateLimiter.execute
      } yield {
        assertCompletes &&
          results.map(r => assert(r)(equalTo(true))).reduce(_ && _) &&
          assert(shouldFail)(equalTo(false)) &&
          assert(shouldSucceed)(equalTo(true))
      }
    }
  )
}
