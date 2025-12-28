package exercises.ch02.ex2

import exercises.ch02.ex2.Cache.cache
import zio.{Clock, Console, ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeUnit

object Cache extends ZIOAppDefault {
  private var cache: Map[String, (String, Long)] =
    Map() // key: String -> (value: String,  expiry, Int)

  def store(key: String, value: String, expireMillis: Long) = {
    for {
      now <- Clock.currentTime(TimeUnit.MILLISECONDS)
      _ <- ZIO.attempt {
        cache = cache.filter { case (k, (v, exp)) => exp > now }
      }
      _ <- ZIO.attempt {
        cache = cache.updated(key, (value, now + expireMillis))
      }
    } yield ()
  }

  def retrieve(key: String) = {
    for {
      now <- Clock.currentTime(TimeUnit.MILLISECONDS)
      _ <- ZIO.attempt {
        cache = cache.filter { case (k, (v, exp)) => exp > now }
      }
      result <- ZIO.attempt(cache.get(key))
    } yield result.map(t => t._1)
  }

  def run = {
    for {
      _ <- store("a", "apple", 100)
      _ <- store("b", "banana", 10000)
      _ <- ZIO.attempt().delay(500.millis)
      a <- retrieve("a")
      b <- retrieve("b")
      _ <- Console.printLine(s"a: $a, b: $b")
    } yield ()
  }
}
