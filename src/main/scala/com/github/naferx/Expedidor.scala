package com.github.naferx

import akka.actor.{Actor, ActorLogging, Props}
import akka.pattern.pipe

import scala.concurrent.Future


object Expedidor {

  def props: Props = Props(new Expedidor)

  final case class ExpidePoliza(id: String)
  final case class PolizaExpedida(id: String)
}


final class Expedidor extends Actor with ActorLogging {
  import context.dispatcher
  import Expedidor._

  override def receive = {
    case comando: ExpidePoliza =>
      log.info(s"Expidiendo poliza....${comando.id}")
      registraPoliza(comando.id).map(_ => PolizaExpedida(comando.id)) pipeTo sender()
  }

  def registraPoliza(id: String): Future[Int] = Future.successful(1)

}
