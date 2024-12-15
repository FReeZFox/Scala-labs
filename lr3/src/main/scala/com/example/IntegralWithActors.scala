package com.example

import akka.actor.typed.{ActorRef, ActorSystem, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object IntegralActor {
  case class Calculate(f: Double => Double, start: Double, stepSize: Double, steps: Int, replyTo: ActorRef[Result])
  case class Result(partialSum: Double)

  def apply(): Behavior[Calculate] = Behaviors.receive { (context, message) =>
    val sum = (0 until message.steps)
      .map(i => message.start + i * message.stepSize)
      .map(x => message.f(x) * message.stepSize) 
      .reduce(_ + _)

    message.replyTo ! Result(sum)
    Behaviors.same
  }
}

object SumActor {
  case class AddPartialResult(partialSum: Double)

  def apply(totalParts: Int, replyTo: ActorRef[Double]): Behavior[AddPartialResult] = Behaviors.setup { (context) =>
    def process(currentSum: Double, remainingParts: Int): Behavior[AddPartialResult] = {
      Behaviors.receiveMessage {
        case AddPartialResult(partialSum) =>
          val updatedSum = currentSum + partialSum
          context.log.info(s"Partial sum: $partialSum, remaining parts: ${remainingParts - 1}")

          if (remainingParts - 1 == 0) {
            replyTo ! updatedSum
            Behaviors.stopped
          } else {
            process(updatedSum, remainingParts - 1)
          }
      }
    }

    process(0.0, totalParts)
  }
}

object FinalSum {
  def apply(): Behavior[Double] = Behaviors.receive { (context, result) =>
    context.log.info(s"Final sum: $result")
    Behaviors.same
  }
}

object MainActor {
  case class StartCalculation(f: Double => Double, l: Double, r: Double, steps: Int)

  def apply(): Behavior[StartCalculation] = Behaviors.setup { context =>
    val integralActor = context.spawn(IntegralActor(), "integralActor")

    Behaviors.receiveMessage {
      case StartCalculation(f, l, r, steps) =>
        val stepSize = (r - l) / steps
        val numActors = 4

        val finalSum = context.spawn(FinalSum(), "finalSum")
        val sumActor = context.spawn(SumActor(numActors, finalSum), "sumActor")

        def sendCalculation(start: Double, steps: Int, replyTo: ActorRef[IntegralActor.Result]): Unit =
          integralActor ! IntegralActor.Calculate(f, start, stepSize, steps, replyTo)

        (0 until numActors)
        .foreach { i =>
          val start = l + i * (steps / numActors) * stepSize
          val replyTo = context.spawn(Behaviors.receiveMessage[IntegralActor.Result] {
            case IntegralActor.Result(partialSum) =>
              sumActor ! SumActor.AddPartialResult(partialSum)
              Behaviors.same
          }, s"responseActor-$i")

          sendCalculation(start, steps / numActors, replyTo)
        }
        
        Behaviors.same
    }
  }
}

@main def integralWithActors(): Unit = {
  val system = ActorSystem(MainActor(), "integralSystem")

  //system ! MainActor.StartCalculation(x => x * x, "x^2", 0, 10, 1000)  // Интеграл x^2 от 0 до 10 (в результате ~333.333)
  system ! MainActor.StartCalculation(x => math.sqrt(x * x + 2), 0, 10, 1000)  // Интеграл sqrt(x^2 + 2) от 0 до 10 (в результате ~53.152)
  //system ! MainActor.StartCalculation(x => math.exp(-x), "exp(-x)", 0, 10, 1000)  // Интеграл exp(-x) от 0 до 10 (в результате ~0.999)
}
