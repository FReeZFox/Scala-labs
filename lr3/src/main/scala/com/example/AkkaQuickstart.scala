package com.example

import akka.actor.typed.ActorRef
import akka.actor.typed.ActorSystem
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors
import com.example.GreeterMain.SayHello

// Greeter (встречающий), актор предназначенный для отправки сообщений
object Greeter {
  // Сообщение Greet, отправляет запрос на сообщение, в поле whom можно указать адресата, а в replyTo ссылку на него
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  // Сообщение Greet, отправляет ответ на сообщение, в поле whom можно указать адресата, а в replyTo ссылку на него
  final case class Greeted(whom: String, from: ActorRef[Greet])
  // Описание поведения актора при приеме сообщения
  def apply(): Behavior[Greet] = Behaviors.receive { (context, message) =>
    // Логирование информации о сообщении в консоль
    context.log.info("Hello {}!", message.whom)
    // Отправка ответа актору, который был указанн в поле replyTo
    message.replyTo ! Greeted(message.whom, context.self)
    // Сохраняем поведение актора после отпарвки сообщения
    Behaviors.same
  }
}

// Актор, который нужен для отправки многих сообщений
object GreeterBot {
  // Описание поведения актора при приеме сообщения
  def apply(max: Int): Behavior[Greeter.Greeted] = {
    bot(0, max)   // Начальное состояние актора
  }
  // Вспомогательная функция, которая помогает все обрабатывать рекурсивно
  // Принимет текущее число сообщений и максимальное
  private def bot(greetingCounter: Int, max: Int): Behavior[Greeter.Greeted] =
    Behaviors.receive { (context, message) =>
      // Увеличение счётчика полученных сообщений
      val n = greetingCounter + 1
      // Логирование информации о сообщении в консоль
      context.log.info("Greeting {} for {}", n, message.whom)
      // Проверяем, достигнуто ли максимальное число сообщений
      if (n == max) {
        // Завершаем работу, если максимальное число достигнуто
        Behaviors.stopped
      } else {
        // Отправка нового сообщения Greet обратно к Greeter
        message.from ! Greeter.Greet(message.whom, context.self)
        // Переходим к следующему состоянию актора 
        bot(n, max)
      }
    }
}

// Актор, предназначенный для управления взаимодействием между Greeter и GreeterBot
object GreeterMain {
  // Сообщение SayHello используется для отправки приветствий, принимает имя, кого поприветствовать
  final case class SayHello(name: String)
  // Описание поведения актора
  def apply(): Behavior[SayHello] =
    Behaviors.setup { context =>
      // Создаем актора
      val greeter = context.spawn(Greeter(), "greeter")
      // Обрабатываем сообщения SayHello
      Behaviors.receiveMessage { message =>
        // Создание актора-бота для многократных приветствий
        val replyTo = context.spawn(GreeterBot(max = 3), message.name)
        // Отправка сообщения от Greet к Greeter
        greeter ! Greeter.Greet(message.name, replyTo)
        // Сохраняем поведение актора
        Behaviors.same
      }
    }
}

object AkkaQuickstart extends App {
  val greeterMain: ActorSystem[GreeterMain.SayHello] = ActorSystem(GreeterMain(), "AkkaQuickStart")
  greeterMain ! SayHello("Charles")
}

