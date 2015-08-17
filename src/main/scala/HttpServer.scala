import java.util.concurrent.TimeUnit

import akka.actor.{ActorSystem, PoisonPill, Props}
import akka.io.IO
import akka.util.Timeout
import spray.can.Http


object Boot extends App {
  implicit val system = ActorSystem("SprayServer")
  val responder = system.actorOf(Props(responseActor))
  implicit val timeout = Timeout(50, TimeUnit.SECONDS)
  val restService = system.actorOf(Props(new ServiceActor), "api-endpoint")
  IO(Http) ! Http.Bind(restService, "localhost", 1080)

  Runtime.getRuntime.addShutdownHook(new Thread() {
    override def run() = {
      restService ! PoisonPill
    }
  })

}
