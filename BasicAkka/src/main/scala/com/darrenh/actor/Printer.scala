package com.darrenh.actor {
  import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
  import scala.io.StdIn

  object Printer {
    def props: Props = Props[Printer]

    final case class Greeting(greeting: String)
  }

  class Printer extends Actor with ActorLogging {
    import Printer._

    def receive = {
      case Greeting(greeting) =>
        log.info(s"Greeting received (from ${sender()}): $greeting")
    }
  }
}