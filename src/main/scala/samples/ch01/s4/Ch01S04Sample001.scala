package samples.ch01.s4


final case class ZIO[-R, +E, +A](
  run: R => Either[E, A]
) {
  self =>
  def map[B](f: A => B): ZIO[R, E, B] =
    ZIO(r => self.run(r).map(f))

  def flatMap[R1 <: R, E1 >: E, B](
    f: A => ZIO[R1, E1, B]
  ): ZIO[R1, E1, B] =
    ZIO(r => self.run(r).fold(ZIO.fail(_), f).run(r))
}

object ZIO {
  def attempt[A](a: => A): ZIO[Any, Throwable, A] =
    ZIO(_ =>
      try Right(a)
      catch {
        case t: Throwable => Left(t)
      }
    )

  def fail[E](e: => E): ZIO[Any, E, Nothing] =
    ZIO(_ => Left(e))
}

object Ch01S04VSample001 extends App {
  // Produces an Int but can throw
  def readInt: Int = scala.io.StdIn.readInt()

  // Produces either an Int or a Throwable
  val zReadInt: ZIO[Any, Throwable, Int] = ZIO.attempt(scala.io.StdIn.readInt())

  val zReadDivision: ZIO[Any, Throwable, Int] =
    for {
      x <- zReadInt
      y <- zReadInt
    } yield x / y

  def zPrintLn(line: String) = ZIO.attempt(println(line))

  val zDivideAndPrint =
    for {
      result <- zReadDivision
      _ <- zPrintLn(s"result")
    } yield ()

  //etc. - no way to run this yet?
}