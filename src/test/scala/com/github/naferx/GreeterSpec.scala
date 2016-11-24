package com.github.naferx


import akka.actor.ActorSystem
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

object GreeterSpec {
  val cfg =
    /*ConfigFactory.parseString("""
      akka {
          loglevel = "DEBUG"
      }
                              """.stripMargin)*/
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

final class GreeterSpec
    extends BaseActorSpec(
      ActorSystem("greeterSpecSystem", GreeterSpec.cfg)) {

import Greeter._
  override def afterAll(): Unit = shutdown()

  "Greeter" should {
    "saludar con un mensaje" in {

      val actorRef = system.actorOf(Greeter.props, "GreeterSpec")
      within(500 millis) {
        actorRef ! "Software Developer"
        expectMsg("Hello Software Developer!")
        actorRef ! "Pepito"
        expectMsg("Hello Pepito!")
        actorRef ! -2 //fire and forget
        val p = Persona("David", 23)
        actorRef ! p
        //expectMsg(Curso(_, _))///
        expectMsgClass(classOf[Curso])

      }
    }
 }
}
