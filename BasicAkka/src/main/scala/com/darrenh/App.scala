//#full-example
package com.darrenh {

    import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
    import scala.io.StdIn
    import com.darrenh.actor.{ Greeter, Printer }

    //#main-class
    object AkkaQuickstart extends App {
    import Greeter._

    // Create the 'helloAkka' actor system
    val system: ActorSystem = ActorSystem("helloAkka")

    try {
        //#create-actors
        // Create the printer actor
        val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

        // Create the 'greeter' actors
        val howdyGreeter: ActorRef =
        system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
        val helloGreeter: ActorRef =
        system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
        val goodDayGreeter: ActorRef =
        system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")
        //#create-actors

        //#main-send-messages
        howdyGreeter ! WhoToGreet("Akka")
        howdyGreeter ! Greet

        howdyGreeter ! WhoToGreet("Lightbend")
        howdyGreeter ! Greet

        helloGreeter ! WhoToGreet("Scala")
        helloGreeter ! Greet

        goodDayGreeter ! WhoToGreet("Play")
        goodDayGreeter ! Greet
        //#main-send-messages

        println(">>> Press ENTER to exit <<<")
        StdIn.readLine()
    } finally {
        system.terminate()
    }
    }
}