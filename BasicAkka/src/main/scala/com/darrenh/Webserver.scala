import akka.actor._
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.cluster.Cluster
import akka.cluster.http.management.ClusterHttpManagement
import com.typesafe.config.{ConfigValueFactory, ConfigFactory}
import akka.stream.ActorMaterializer
import scala.io.StdIn
import com.darrenh.actor.MonitorActor

object WebServer {
  def main(args: Array[String]) {
    implicit val system = ActorSystem("my-system")

    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val cluster = Cluster(system)
    ClusterHttpManagement(cluster).start()

    system.actorOf(Props[MonitorActor], "cluster-monitor")

    val route =
      path("hello") {
        get {
          println("Got request")
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>Say hello to akka-http</h1>"))
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "0.0.0.0", 8080)

    println(s"Server is totally up at http://0.0.0.0:8080/")
    println(s"Press RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}