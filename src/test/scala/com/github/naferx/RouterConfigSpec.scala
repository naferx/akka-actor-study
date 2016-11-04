package com.github.naferx


import akka.actor.{ActorRef, ActorSystem}
import akka.routing.FromConfig
import com.github.naferx.Expedidor.{ExpidePoliza, PolizaExpedida}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

object RouterConfigSpec {
  val cfg =
    ConfigFactory.parseString("""
    akka {
      loggers = ["akka.testkit.TestEventListener"]
      loglevel = "INFO"
      actor.deployment {
        /cotizadores {
          router = random-pool
          nr-of-instances = 5
        }
      }
    }""".stripMargin)
}


final class RouterConfigSpec
  extends BaseActorSpec(
    ActorSystem("routerConfigSpec", RouterConfigSpec.cfg)) {

  override def afterAll(): Unit = shutdown()

  "BalanceadorCotizador" should {
    "crear un pool de actores de una configuracion" in {

      val routerExpedidoresRef: ActorRef =
        system.actorOf(FromConfig.props(Expedidor.props), "cotizadores")

      within(500 millis) {
        routerExpedidoresRef ! ExpidePoliza("poliza#81721")
        expectMsg(PolizaExpedida("poliza#81721"))

        routerExpedidoresRef ! ExpidePoliza("poliza#89864")
        expectMsg(PolizaExpedida("poliza#89864"))

        routerExpedidoresRef ! ExpidePoliza("poliza#31827")
        expectMsg(PolizaExpedida("poliza#31827"))
      }
    }
  }
}