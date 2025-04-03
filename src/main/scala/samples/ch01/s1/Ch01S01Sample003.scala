package samples.ch01.s1

import zio.{ZIO, ZIOAppDefault, durationInt}

import java.util.concurrent.TimeUnit._
import java.util.concurrent.{Executors, ScheduledExecutorService}


object Ch01S01Sample003a extends ZIOAppDefault {

  val goShopping =
    ZIO.attempt(println("Going to the grocery store"))

  val run = goShopping
}

object Ch01S01Sample003b extends ZIOAppDefault {

  val goShoppingLater =
    ZIO.attempt(println("Going to the grocery store"))
      .delay(5.seconds)

  val run = goShoppingLater
}