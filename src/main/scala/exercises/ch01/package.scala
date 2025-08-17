package object exercises {

  final case class ZIO[-R, +E, +A](run: R => Either[E, A])

  def incrementUnlessNegative(x: Int) =
    if (x >= 0) Right(x + 1)
    else Left("Nope, no negatives allowed")

  def doubleUnlessOdd(x: Int) =
    if (x % 2 == 0) Right(2 * x)
    else Left("Nein danke, too odd")
}
