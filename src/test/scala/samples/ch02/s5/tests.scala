package samples.ch02.s5

import zio.test._
import zio.test.Assertion._

object ExampleSpec5 extends ZIOSpecDefault {

  def spec = suite("ExampleSpec")(
    test("integer addition is associative") {
      check(intGen, intGen, intGen) { (x, y, z) =>
        val left = (x + y) + z
        val right = x + (y + z)
        assert(left)(equalTo(right))
      }
    }
  )
}