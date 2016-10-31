package com.github.naferx

import akka.actor.ActorSystem
import com.github.naferx.BalanceadorCotizadores.{AgregaCotizador, CotizaPoliza}
import com.github.naferx.Cotizador.PolizaCotizada
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object BalanceadorCotizadorSpec {
  val cfg =
    ConfigFactory.parseString("""
      akka {
        loggers = ["akka.testkit.TestEventListener"]
        loglevel = "INFO"
        log-dead-letters = 10
        log-dead-letters-during-shutdown = on
        actor {
          debug {
            autoreceive = on
            lifecycle = on
          }
        }
      }
                              """.stripMargin)
}

final class BalanceadorCotizadorSpec
  extends BaseActorSpec(
    ActorSystem("balanceadorCotizadorSpec", BalanceadorCotizadorSpec.cfg)) {

  override def afterAll(): Unit = shutdown()

  "BalanceadorCotizador" should {
    "reenviar un mensaje para CotizarPoliza" in {

      val cotizador1Ref = system.actorOf(Cotizador.props("cotizador1"), "actor-cotizador1")
      val cotizador2Ref = system.actorOf(Cotizador.props("cotizador2"), "actor-cotizador2")

      val balanceador = system.actorOf(BalanceadorCotizadores.props, "balanceador-cotizadores")

      within(500 millis) {
        balanceador ! AgregaCotizador(cotizador1Ref)
        balanceador ! AgregaCotizador(cotizador2Ref)

        balanceador ! CotizaPoliza("SEG-00001")
        expectMsg(PolizaCotizada("SEG-00001"))

        balanceador ! CotizaPoliza("SEG-00002")
        expectMsg(PolizaCotizada("SEG-00002"))
      }
    }
  }
}
