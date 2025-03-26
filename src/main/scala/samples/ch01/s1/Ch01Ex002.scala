package samples.ch01.s1

import java.util.concurrent.{Executors, ScheduledExecutorService}
import java.util.concurrent.TimeUnit._


object Ch01Ex002 extends App {

  val goShoppingUnsafe: Unit = {
    println("Going to the grocery store")
  }

  val scheduler: ScheduledExecutorService =
    Executors.newScheduledThreadPool(1)

  scheduler.schedule(
    new Runnable {
      def run: Unit = goShoppingUnsafe
    },
    10,
    SECONDS
  )
  scheduler.shutdown()
}
