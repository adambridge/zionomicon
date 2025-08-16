package exercises

import zio.{Console, ZIO, ZIOAppDefault}
import java.io.IOException


object Ch01Exercise19 extends ZIOAppDefault {
  // 19. Using the Console service and recursion, write a function that will repeatedly reads
  // input from the console until the specified user-defined function evaluates to true
  // on the input.

  def readUntil(
    acceptInput: String => Boolean
  ): ZIO[Any, IOException, String] =
    for {
      input <- Console.readLine("Enter input:\n")
      acceptedInput <- if (acceptInput(input)) ZIO.succeed(input) else readUntil(acceptInput)
    } yield (acceptedInput)

  // Check stack safety by running this forever:
  def readForever: ZIO[Any, IOException, String] =
    for {
      _ <- Console.print(".")
      _ <- readForever
    } yield "Never getting here"

  val run = readUntil(_ == "hello")

//  val run = readForever
}

