package com.github.naferx

import akka.actor.{ Actor, Props }
import akka.actor.ActorSystem
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Future


object ActoresApp extends App {
  import Greeter._

  implicit val timeout = Timeout(2.seconds)

  val system = ActorSystem("actores-app-system")
  val actorRef = system.actorOf(Greeter.props, "greeter")

  val p = Persona("David", 23)
  val curso: Future[Persona] = (actorRef ? p).mapTo[Persona]

  curso.map { c =>
    println(s"=> ${c}")
  }.recover {
    case e: Throwable =>
    println(s"Error => ${e.getMessage}")
  }

  //? ask pattern
  //DeadLetters
}
