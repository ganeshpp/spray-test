

import java.util.concurrent.TimeUnit

import Boot._
import akka.actor._
import spray.routing.RejectionHandler._
import spray.routing.{HttpService, RequestContext}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.util._

trait ApiService extends HttpService {
  // implicit val ec: ExecutionContext = actorRefFactory.dispatcher
  val apiRouter = {
    path("increment" / IntNumber) { num =>
      get {
        ctx => system.actorOf(Props(new requestActor(ctx, testMsg(num))))
      }
    }
  }

}

class ServiceActor extends Actor with ApiService {
  implicit val system = context.system

  def actorRefFactory = context

  def receive = runRoute(apiRouter)
}

class requestActor(ctx: RequestContext, request: testMsg) extends Actor with ActorLogging {
  responder ! request
  context.setReceiveTimeout(100, TimeUnit.SECONDS)

  def receive = {
    case fst: Future[testMsg] => fst.onComplete {
      case Success(x) => x match {
        case testMsg(n:Int) => ctx.complete(n.toString)
      }
      case Failure(e) => ctx.complete(e.getMessage)
    }
      self ! PoisonPill

    case Failure(e) => e.printStackTrace
      ctx.complete(e.getMessage)
      self ! PoisonPill
    case _ => println("unknown message received")
      ctx.complete("Internal Error")
      self ! PoisonPill

  }
}

case class testMsg(num: Int)

object responseActor extends Actor with ActorLogging {
  context.setReceiveTimeout(100, TimeUnit.SECONDS)

  def receive = {
    case x: testMsg =>
      sender ! Future {
        testMsg(x.num + 1)
      }
    case _ => println("unknown request")
  }
}

