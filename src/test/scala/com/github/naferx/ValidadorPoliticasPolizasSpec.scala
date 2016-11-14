package com.github.naferx


import akka.actor.{ActorRef, ActorSystem, Props, Terminated}
import com.typesafe.config.{Config, ConfigFactory}
import org.scalatest.{BeforeAndAfterAll, FlatSpecLike, Matchers}
import akka.testkit.{EventFilter, ImplicitSender, TestActors, TestKit}

class ValidadorPoliticasPolizasSpec(_system: ActorSystem) extends TestKit(_system)
  with ImplicitSender with FlatSpecLike with Matchers with BeforeAndAfterAll {

  import ValidadorPoliticasPolizas._

  def this() = this(ActorSystem(
    "ValidadorPoliticasPolizasSpec",
    ConfigFactory.parseString("""
      akka {
        loggers = ["akka.testkit.TestEventListener"]
        loglevel = "WARNING"
      }
                              """)))

  override def afterAll {
    TestKit.shutdownActorSystem(system)
  }

  "A supervisor" must "apply the chosen strategy for its child" in {
      val supervisor = system.actorOf(Props[Supervisor], "supervisor")

      supervisor ! Props[ValidadorPoliticasPolizas]
      val child = expectMsgType[ActorRef] // retrieve answer from TestKit’s testActor

    child ! Poliza(23)// set state to 42
    child ! GetPoliticasValidadas
    expectMsg(23)

    child ! new ArithmeticException // crash it
    child ! GetPoliticasValidadas
    expectMsg(23)

    child ! new NullPointerException // crash it harder
    child ! GetPoliticasValidadas
    expectMsg(0)

    watch(child) // have testActor watch “child”
    child ! new IllegalArgumentException // break it
    expectMsgPF() { case Terminated(`child`) => () }
  }

}