package samples.ch01.s3

import scala.io.StdIn
import zio.{ZIO, ZIOAppDefault}

object Ch01S03Sample001a extends ZIOAppDefault {
  val firstName =
    ZIO.attempt(StdIn.readLine("What is your first name?"))

  val lastName =
    ZIO.attempt(StdIn.readLine("What is your last name"))

  val fullName =
    firstName.zipWith(lastName)((first, last) => s"$first $last")

  val run = fullName
}

object Ch01S03Sample001b extends ZIOAppDefault {
  val firstName =
    ZIO.attempt(StdIn.readLine("What is your first name?"))

  val lastName =
    ZIO.attempt(StdIn.readLine("What is your last name"))

  val fullName =
    firstName.zipWith(lastName)((first, last) => s"$first $last")

  def printLine(line: String) =
    ZIO.attempt(println(line))

  val printFullName =
  for {
    n <- fullName
    _ <- printLine(n)
  } yield ()

  val run = printFullName
}
