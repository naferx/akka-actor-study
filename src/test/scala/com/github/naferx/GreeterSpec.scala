package com.github.naferx


import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object GreeterSpec {
  val cfg =
    ConfigFactory.parseString("""
      akka {
          loglevel = "DEBUG"
      }
                              """.stripMargin)
}

final class GreeterSpec
    extends BaseActorSpec(
      ActorSystem("greeterSpecSystem", GreeterSpec.cfg)) {

  override def afterAll(): Unit = shutdown()

  "Greeter" should {
    "saludar con un mensaje" in {

      val actorRef = system.actorOf(Greeter.props, "GreeterSpec")
      within(500 millis) {
        actorRef ! "Software Developer"
        expectMsg("Hello Software Developer!")
        actorRef ! "Pepito"
        expectMsg("Hello Pepito!")
        actorRef ! 1
      }
    }
 }
}
