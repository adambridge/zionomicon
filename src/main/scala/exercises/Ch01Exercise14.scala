package exercises

import exercises.Ch01Exercise13.currentTimeZIO
import zio.{ZIO, ZIOAppDefault}

import java.lang.Throwable
import scala.util.Try

object Ch01Exercise14 extends ZIOAppDefault {
  // 14. Using ZIO.async, convert the following asynchronous, callback-based function
  // into a ZIO function:

  def getCacheValue(
    key: String,
    onSuccess: String => Unit,
    onFailure: Throwable => Unit
  ): Unit = {
    // Simulating an asynchronous operation
    Try {
      // Simulate fetching value from cache
      if (key == "validKey") {
        onSuccess("CachedValue")
      } else {
        throw new RuntimeException("Key not found")
      }
    }.recover {
      case e: Throwable => onFailure(e)
    }
  }

  def getCacheValueZio(key: String): ZIO[Any, Throwable, String] =
    ZIO.async { callback =>
      getCacheValue(
        key,
        result => callback(ZIO.succeed(result)),
        error => callback(ZIO.fail(error))
      )
    }

  val run = {
    for {
      // result <- getCacheValueZio("validKey")
      // Uncomment the next line to test with an invalid key
      result <- getCacheValueZio("invalidKey")
    } yield println(s"result: $result")
  }
}

