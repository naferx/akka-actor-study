package com.github.naferx


import akka.actor.{Actor, Props, Terminated}
import akka.routing.{ActorRefRoutee, RoundRobinRoutingLogic, Router}

object RouterExpedidores {
  def props: Props = Props(new RouterExpedidores)
}

final class RouterExpedidores extends Actor {
  import context._
  import Expedidor._

  var router = {
    val routees = Vector.fill(5) {
      val r = actorOf(Expedidor.props)
      context watch r
      ActorRefRoutee(r)
    }
    Router(RoundRobinRoutingLogic(), routees)
  }

  def receive = {
    case e: ExpidePoliza =>
      router.route(e, sender())
    case Terminated(a) =>
      router = router.removeRoutee(a)
      val r = context.actorOf(Expedidor.props)
      context watch r
      router = router.addRoutee(r)
  }
}
