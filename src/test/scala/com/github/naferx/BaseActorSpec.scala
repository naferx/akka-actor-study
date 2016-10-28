package com.github.naferx

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestKit}
import org.scalatest._

/**
  * Base para pruebas unitarias
  */
trait BaseSpec
    extends Matchers
    with OptionValues
    with Inside
    with Inspectors
    with WordSpecLike

/**
  * Clase base para pruebas de actores
  */
abstract class BaseActorSpec(override val system: ActorSystem)
    extends TestKit(system)
    with BaseSpec
    with DefaultTimeout
    with ImplicitSender
    with BeforeAndAfterAll
