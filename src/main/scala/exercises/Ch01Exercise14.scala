package exercises

import zio.{ZIO, ZIOAppDefault}

object Ch01Exercise14 extends ZIOAppDefault {
  // 14. Using ZIO.async, convert the following asynchronous, callback-based function
  // into a ZIO function:

  def getCacheValue(
    key: String,
    onSuccess: String => Unit,
    onFailure: Throwable => Unit
  ): Unit = key match {
    case "valid-key" => onSuccess("cached-value")
    case _ => onFailure(new Throwable("not-found"))
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
//      result <- getCacheValueZio("valid-key")
//      Uncomment the next line to test with an invalid key
      result <- getCacheValueZio("invalid-key")
    } yield println(s"result: $result")
  }
}

