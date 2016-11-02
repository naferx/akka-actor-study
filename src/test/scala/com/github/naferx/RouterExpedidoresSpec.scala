package com.github.naferx

import akka.actor.ActorSystem
import com.github.naferx.Expedidor.{ExpidePoliza, PolizaExpedida}
import com.typesafe.config.ConfigFactory

object RouterExpedidoresSpec {
  val cfg =
    ConfigFactory.parseString("""
      akka {
        loggers = ["akka.testkit.TestEventListener"]
        loglevel = "DEBUG"
      }
                              """.stripMargin)
}


final class RouterExpedidoresSpec extends BaseActorSpec(ActorSystem("routerExpedidoresSpec", RouterExpedidoresSpec.cfg)) {

  "RouterExpedidores" should {

    "crear varias instancias de expedidores" in {
      val routerExpedidoresRef = system.actorOf(RouterExpedidores.props, "router-expedidores")
      routerExpedidoresRef ! ExpidePoliza("poliza#81721")
      expectMsg(PolizaExpedida("poliza#81721"))

      routerExpedidoresRef ! ExpidePoliza("poliza#89864")
      expectMsg(PolizaExpedida("poliza#89864"))

      routerExpedidoresRef ! ExpidePoliza("poliza#31827")
      expectMsg(PolizaExpedida("poliza#31827"))
    }

  }

}
