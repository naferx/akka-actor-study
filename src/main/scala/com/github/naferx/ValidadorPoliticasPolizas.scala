package com.github.naferx


import akka.actor.{Actor, Props}


final class Supervisor extends Actor {
  import akka.actor.OneForOneStrategy
  import akka.actor.SupervisorStrategy._
  import scala.concurrent.duration._

  override val supervisorStrategy =
    OneForOneStrategy(maxNrOfRetries = 10, withinTimeRange = 1 minute) {
      case _: ArithmeticException => Resume
      case _: NullPointerException => Restart
      case _: IllegalArgumentException => Stop
      case _: Exception => Escalate
    }

  override def receive: Receive = {
    case p: Props => sender() ! context.actorOf(p)
  }
}


object ValidadorPoliticasPolizas {
  def props: Props = Props(new ValidadorPoliticasPolizas)

  final case class Poliza(valor: Int)
  case object GetPoliticasValidadas
}

final class ValidadorPoliticasPolizas extends Actor {
  import ValidadorPoliticasPolizas._

  var politicas = 0

  override def receive: Receive = {
    case ex: Exception => throw ex
    case poliza: Poliza => politicas = politicas + poliza.valor
    case GetPoliticasValidadas => sender() ! politicas
  }

}
