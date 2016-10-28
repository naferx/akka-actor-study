package com.github.naferx


import akka.actor.{ Actor, Props }



object Greeter {
  def props: Props = Props(new Greeter)
}

final class Greeter extends Actor {
  override def receive = {
    case name: String => sender() ! s"Hello $name!"
  }
}
