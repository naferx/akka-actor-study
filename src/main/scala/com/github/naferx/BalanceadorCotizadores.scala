package com.github.naferx


import akka.actor.{Actor, ActorRef, Props}

import scala.collection._

object BalanceadorCotizadores {
  def props: Props = Props(new BalanceadorCotizadores)

  final case class CotizaPoliza(poliza: String)
  final case class AgregaCotizador(cotizador: ActorRef)
}


final class BalanceadorCotizadores extends Actor {
  import BalanceadorCotizadores._

  var cotizadores: mutable.Seq[ActorRef] = mutable.Seq.empty[ActorRef]

  override def receive = {
    case comando: CotizaPoliza =>
      getCotizador forward comando
    case nuevoCotizador: AgregaCotizador =>
      cotizadores = cotizadores :+ nuevoCotizador.cotizador
  }

  def getCotizador: ActorRef = {
    cotizadores.head
  }

}







