package samples.ch02.s1


import zio.test._
import zio.test.Assertion._
import zio.ZIO

object ExampleSpec2 extends ZIOSpecDefault {

  def spec = suite("ExampleSpec")(
    test("hasSameElement") {
      assert(List(1, 1, 2, 3))(hasSameElements(List(3, 2, 1, 1)))
    },
    test("fails") {
      for {
        exit <- ZIO.attempt(1 / 0).catchAll(_ => ZIO.fail(())).exit
      } yield assert(exit)(fails(isUnit))
    }
  )
}
