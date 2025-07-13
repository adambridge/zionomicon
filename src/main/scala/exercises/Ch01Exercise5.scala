package exercises

import zio.{ZIO, ZIOAppDefault}


object Ch01Exercise5 extends ZIOAppDefault {
  // 5. Rewrite the following ZIO code that uses flatMap into a for comprehension.

  val random = ZIO.attempt(scala.util.Random.
    nextInt(3) + 1)
  def printLine(line: String) = ZIO.attempt(println(line))
  val readLine = ZIO.attempt(scala.io.StdIn.readLine())

  val example = random.flatMap { int =>
    printLine("Guess a number from 1 to 3:").flatMap { _ =>
      readLine.flatMap { num =>
        if (num == int.toString) printLine("You guessed right!")
        else printLine(s"You guessed wrong, the number was $int!")
      }
    }
  }

  val run = for {
    int <- random
    _ <- printLine("Guess a number from 1 to 3:")
    guess <- readLine
    _ <- if (guess == int.toString) printLine("You guessed right!")
         else printLine(s"You guessed wrong, the number was $int!")
  } yield ()
}
