package exercises.ch01

import zio.{ZIO, ZIOAppDefault}

import scala.util.Try

object Ch01Exercise15 extends ZIOAppDefault {
  // 15. Using ZIO.async, convert the following asynchronous, callback-based function
  // into a ZIO function:

  trait User

  sealed case class NamedUser(name: String) extends User

  def saveUserRecord(
    user: User,
    onSuccess: () => Unit,
    onFailure: Throwable => Unit
  ): Unit = user match {
      case NamedUser("valid-user") => onSuccess()
      case _ => onFailure(new Throwable("wah-wah"))
    }

  def saveUserRecordZio(user: User): ZIO[Any, Throwable, Unit] =
    ZIO.async { callback =>
      saveUserRecord(
        user,
        () => callback(ZIO.succeed()),
        error => callback(ZIO.fail(error))
      )
    }

  val run = {
    for {
//      result <- saveUserRecordZio(NamedUser("valid-user"))
//      Uncomment the next line to test with an invalid key
      result <- saveUserRecordZio(NamedUser("invalid-user"))
    } yield println(s"result: $result")
  }
}

