package exercises.ch02.ex2

import zio.{Clock, Console, ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeUnit

class Cache {
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
}

object Cache extends ZIOAppDefault {
  def run = {
    val cache = new Cache
    for {
      _ <- cache.store("a", "apple", 100)
      _ <- cache.store("b", "banana", 10000)
      _ <- ZIO.attempt().delay(500.millis)
      a <- cache.retrieve("a")
      b <- cache.retrieve("b")
      _ <- Console.printLine(s"a: $a, b: $b")
    } yield ()
  }
}
