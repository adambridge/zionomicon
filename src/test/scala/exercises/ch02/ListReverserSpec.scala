package exercises.ch02

import exercises.ch02.ListReverser.reverse
import zio.test.Assertion.equalTo
import zio.test._

object ListReverserSpec extends ZIOSpecDefault {
  val genInt: Gen[Any, Int] =
    Gen.int
  val genList: Gen[Any, List[Int]] =
    Gen.listOf(genInt)
  def spec = suite("ListReverserSpec")(
    test("reverse should reverse list") {
      check(genList) { l =>
        for {
          reversed <- reverse(l)
          result <- reverse(reversed)
        } yield assert(result)(equalTo(l))
      }
    }
  )
}
