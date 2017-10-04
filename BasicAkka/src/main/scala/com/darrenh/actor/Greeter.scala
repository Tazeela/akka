package com.darrenh.actor {

  import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }

  object Greeter {
    def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))

    final case class WhoToGreet(who: String)
    case object Greet
  }

  class Greeter(message: String, printerActor: ActorRef) extends Actor {
    import Greeter._
    import Printer._

    var greeting = ""

    def receive = {
      case WhoToGreet(who) =>
        greeting = s"$message, $who"
      case Greet           =>
        printerActor ! Greeting(greeting)
    }
  }
}