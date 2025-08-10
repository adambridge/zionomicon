package exercises

import zio.{Console, Random, ZIOAppDefault}


object Ch01Exercise18 extends ZIOAppDefault {
  // 18. Using the Console and Random services in ZIO, write a little program that asks the
  // user to guess a randomly chosen number between 1 and 3 and prints out if they are
  // correct or not.

  val run = {
    for {
      number <- Random.nextInt
      guess <- Console.readLine("What number am I thinking of? 1, 2, or 3\n")
      _ <- if (number == guess) Console.printLine("Right!") else Console.printLine("Nope!")
    } yield ()
  }
}

