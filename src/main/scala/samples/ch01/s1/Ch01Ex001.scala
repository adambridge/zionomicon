package samples.ch01.s1

object Ch01Ex001 extends App {

  val goShoppingUnsafe: Unit = {
    println("Going to the grocery store")
  }

  println("1")
  Thread.sleep(1000)
  println("2")
  Thread.sleep(1000)
  println("3")
  Thread.sleep(1000)

  goShoppingUnsafe
}
