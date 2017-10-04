package com.darrenh {

    import akka.actor.{ Actor, ActorLogging, ActorRef, ActorSystem, Props }
    import scala.io.StdIn
    import com.darrenh.actor.{ Greeter, Printer }

    object AkkaQuickstart extends App {
        import Greeter._

        // Create the 'helloAkka' actor system
        val system: ActorSystem = ActorSystem("helloAkka")

        try {
            // Create the printer actor
            val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

            // Create the 'greeter' actors
            val howdyGreeter: ActorRef =
            system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
            val helloGreeter: ActorRef =
            system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
            val goodDayGreeter: ActorRef =
            system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

            howdyGreeter ! WhoToGreet("Akka")
            howdyGreeter ! Greet

            howdyGreeter ! WhoToGreet("Lightbend")
            howdyGreeter ! Greet

            helloGreeter ! WhoToGreet("Scala")
            helloGreeter ! Greet

            goodDayGreeter ! WhoToGreet("Play")
            goodDayGreeter ! Greet

            println(">>> Press ENTER to exit <<<")
            StdIn.readLine()
        } finally {
            system.terminate()
        }
    }
}