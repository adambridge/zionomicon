package samples.ch02.s1


import zio.test._
import zio.test.Assertion._
import zio.ZIO

object ExampleSpec01 extends ZIOSpecDefault {

  def spec = suite("ExampleSpec")(
    test("addition works") {
      assert(1 + 1)(equalTo(2))
    },
    test("ZIO.succeed succeeds with specified value") {
      assertZIO(ZIO.succeed(1 + 1))(equalTo(2))
    },
    test("testing an effect using map operator") {
      ZIO.succeed(1 + 1).map(n => assert(n)(equalTo(2)))
    },
    test("testing an effect using a for comprehension") {
      for {
        n <- ZIO.succeed(1 + 1)
      } yield assert(n)(equalTo(2))
    },
    test("and") {
      for {
        x <- ZIO.succeed(1)
        y <- ZIO.succeed(2)
      } yield assert(x)(equalTo(1)) &&
        assert(y)(equalTo(2))
    }
  )
}
