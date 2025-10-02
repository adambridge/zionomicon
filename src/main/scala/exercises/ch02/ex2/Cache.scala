package exercises.ch02.ex2

import exercises.ch02.ex2.Cache.cache
import zio.{Clock, Console, ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeUnit

object Cache extends ZIOAppDefault {
  private var cache: Map[String, String] = Map()

  def store(key: String, value: String, expireMillis: Int) = {
    for {
      _ <- Console.printLine(s"${Clock.currentTime(TimeUnit.MILLISECONDS)} Storing $key = $value")
      _ <- ZIO.attempt {cache = cache.updated(key, value)}
      _ <- ZIO.attempt {cache = cache.removed(key)}.delay(expireMillis.millis)
    } yield ()
  }

  def retrieve(key: String) = {
    for {
      value <- ZIO.attempt(cache.get(key))
      _ <- Console.printLine(s"${Clock.currentTime(TimeUnit.MILLISECONDS)} Retrieved $key: $value")
    } yield ()
  }

  def run = {
    for {
      _ <- store("a", "apple", 10)
      _ <- store("b", "banana", 1000).delay(50.millis)
      a <- retrieve("a").delay(150.millis)
      b <- retrieve("b").delay(250.millis)
      _ <- Console.printLine(s"a: $a, b: $b")
    } yield ()
  }
}
