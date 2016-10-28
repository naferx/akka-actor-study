package com.github.naferx

import akka.actor.{Actor, ActorLogging, Props}


final class Cotizador(id: String) extends Actor with ActorLogging {
  import Cotizador._
  import BalanceadorCotizadores._

    val cotizadorId : String = id

    override def receive = {
    case comando: CotizaPoliza =>
      log.info(s"Cotizando poliza....${comando.poliza}")
      sender() ! PolizaCotizada(comando.poliza)
  }
}

object Cotizador{
  def props(id: String): Props = Props(new Cotizador(id))

  final case class PolizaCotizada(id: String)
}