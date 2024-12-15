package com.example

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import scala.util.Random

object AddingServer {
  case class AddRequest(a: Int, b: Int, replyTo: ActorRef[AddResponse])
  case class AddResponse(result: Int)

  def apply(): Behavior[AddRequest] = Behaviors.receive { (context, message) =>
    val result = message.a + message.b
    context.log.info(s"Sum: ${message.a} + ${message.b} = $result")
    message.replyTo ! AddResponse(result) 
    Behaviors.same
  }
}

object AddingClient {
  enum CommandType:
    case Start, Response

  case class Command(action: CommandType, data: Option[(Int, String)] = None)

  def apply(server: ActorRef[AddingServer.AddRequest]): Behavior[Command] = Behaviors.setup { (context) =>
    def sendRandomNumbers(): Unit = {
      val a = Random.nextInt(100)
      val b = Random.nextInt(100)
      context.log.info(s"Sending numbers $a and $b to server")
      server ! AddingServer.AddRequest(a, b, context.messageAdapter(r => 
        Command(CommandType.Response, Some((r.result, s"Numbers $a and $b")))
      ))
    }

    Behaviors.receiveMessage {
      case Command(CommandType.Start, _) =>
        sendRandomNumbers()
        Behaviors.same

      case Command(CommandType.Response, Some((result, message))) =>
        context.log.info(s"$message have a sum of $result")
        Behaviors.same

      case _ =>
        Behaviors.same
    }
  }
}

object AddingSystem {
  def apply(): Behavior[Unit] = Behaviors.setup { (context) =>
    val server = context.spawn(AddingServer(), "server")

    (1 to 3)
    .foreach { i => context
    .spawn(AddingClient(server), s"client-$i") 
    ! AddingClient.Command(AddingClient.CommandType.Start)
    }

    Behaviors.empty
  }
}

@main def newAddingSystem(): Unit = {
  val system = ActorSystem(AddingSystem(), "system")
}
