package com.github.naferx


import akka.actor.{ Actor, Props }



object Greeter {
  def props: Props = Props(new Greeter)

  final case class Persona(nombre: String, edad: Int)
  final case class Curso(nombre: String, profesor: String)
}

final class Greeter extends Actor {
  import Greeter._
  override def receive = {
    case name: String => sender() ! s"Hello $name!"
    case value: Int if value > 0 => sender() ! s"You own me $value dollars"
    case p: Persona => sender() ! Curso("BD No relacionales", "Nafer Sanabria")
  }
}
