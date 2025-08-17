package exercises.ch01

import zio.ZIO

object Ch01Exercise04 {
  // 4. Rewrite the following ZIO code that uses flatMapinto a for comprehension.

  def printLine(line: String) = ZIO.attempt(println(line))

  val readLine = ZIO.attempt(scala.io.StdIn.readLine())

  printLine("What is your name?").flatMap(_ =>
    readLine.flatMap(name => printLine(s"Hello, $name!"))
  )

  for {
    _ <- printLine("What is your name?")
    name <- readLine
    _ <- printLine(s"Hello, $name!")
  } yield ()
}
