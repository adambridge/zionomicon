package samples.ch02.s4

import zio.test.Assertion._
import zio.test.TestAspect._
import zio.test._
import zio.ZIO

object ExampleSpec04 extends ZIOSpecDefault {

  def spec = suite("ExampleSpec")(
    test("this test will be repeated to ensure it is stable") {
      assertZIO(ZIO.succeed(1 + 1))(equalTo(2))
    } @@ nonFlaky
  )
}
