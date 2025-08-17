package exercises.ch01

import zio.{ZIOAppDefault, Console}


object Ch01Exercise17 extends ZIOAppDefault {
  // 17. Using the Console, write a little program that asks the user what their name is and
  // then prints it out to them with a greeting.

  val run = {
    for {
      name <- Console.readLine("Hey buddy, what's your name?\n")
      _ <- Console.printLine(s"Nice to meet you, $name!")
    } yield ()
  }
}

