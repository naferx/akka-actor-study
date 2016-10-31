package com.github.naferx

import akka.actor.ActorSystem


import scala.concurrent.duration._


final class ExpedidorSpec extends BaseActorSpec(ActorSystem("expedidorSpec")) {

  import Expedidor._

  "Expedidor" should {

    "expedir una poliza" in {
      val expedidorRef = system.actorOf(Expedidor.props, "actor-expedidor")

      within(500 millis) {
        expedidorRef ! ExpidePoliza("poliza#128371")
        expectMsg(PolizaExpedida("poliza#128371"))
      }
    }

  }
}
