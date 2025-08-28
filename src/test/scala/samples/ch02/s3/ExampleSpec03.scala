package samples.ch02.s3


import zio.test._
import zio.test.Assertion._
import zio.{ZIO, durationInt}

object ExampleSpec03 extends ZIOSpecDefault {

  def spec = suite("ExampleSpec3")(
    test("greet says hello to the user") {
      for {
        _ <- TestConsole.feedLines("Jane")
        _ <- greet
        value <- TestConsole.output
      } yield assert(value)(equalTo(Vector("Hello, Jane!\n")))
    },
    test("goShopping delays for one hour") {
      for {
        fiber <- goShopping.fork
        _ <- TestClock.adjust(1.hour)
        _ <- fiber.join
      } yield assertCompletes
    }
  )
}
