package exercises.ch02.ex3

import zio.{Console, ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeUnit

class RateLimiter(N: Int) {
  private val windowMillis = 5_000 // 5 seconds
  private var eventTimes = List[Long]()

  private def now = zio.Clock.currentTime(TimeUnit.MILLISECONDS)

  private def lessThanOneMinuteOld(timeNow: Long)(eventTime: Long) =
    timeNow - eventTime < windowMillis

  private def nEventsLastMinute = for {
    timeNow <- now
    count <- ZIO.attempt(
      eventTimes.takeWhile(lessThanOneMinuteOld(timeNow)(_)).size
    )
  } yield count

  def execute: ZIO[Any, Throwable, Boolean] = for {
    n <- nEventsLastMinute
    allowed <- ZIO.succeed(n < N)
    timeNow <- now
    _ <- ZIO.attempt {
      if (allowed)
        eventTimes =
          timeNow :: eventTimes.takeWhile(lessThanOneMinuteOld(timeNow)(_))
      else if (allowed)
        eventTimes = eventTimes.takeWhile(lessThanOneMinuteOld(timeNow)(_))
    }
  } yield allowed
}

object RateLimiterApp extends ZIOAppDefault {

  val rateLimiter = new RateLimiter(2)

  def run: ZIO[Any, Throwable, ZIO[Any, Nothing, Unit]] = {
    (for {
      succeeded <- rateLimiter.execute.delay(1.second)
      _ <-
        if (succeeded) Console.printLine("success")
        else Console.printLine(s"fail")
    } yield ZIO.succeed()).repeatN(10)
  }
}
