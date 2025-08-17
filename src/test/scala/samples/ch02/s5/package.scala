package samples.ch02

import zio._
import zio.test._

package object s5 {
  val intGen: Gen[Any, Int] =
    Gen.int

  final case class User(name: String, age: Int)

  val genName: Gen[Random with Sized, String] =
    Gen.asciiString

  val genAge: Gen[Random, Int] =
    Gen.int(18, 120)
  
  val genUser: Gen[Random with Sized, User] =
    for {
      name <- genName
      age <- genAge
    } yield User(name, age)
}
