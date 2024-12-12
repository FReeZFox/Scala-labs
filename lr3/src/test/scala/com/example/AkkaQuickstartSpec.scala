package com.example

import akka.actor.testkit.typed.scaladsl.ScalaTestWithActorTestKit 
import com.example.Greeter.Greet 
import com.example.Greeter.Greeted 
import org.scalatest.wordspec.AnyWordSpecLike 

class AkkaQuickstartSpec extends ScalaTestWithActorTestKit with AnyWordSpecLike {
  // Описание тестов для актора Greeter
  "A Greeter" must {
    // Проверяем что актор Greeter отправляет корректный ответ на сообщение от Greet
    "reply to greeted" in {
      // Тестовый актор, который принимает сообщения типа Greeted
      val replyProbe = createTestProbe[Greeted]()
      // Создание экземпляра актора Greeter
      val underTest = spawn(Greeter())
      // Отправка сообщения от Greet актору Greeter с именем "Santa"
      underTest ! Greet("Santa", replyProbe.ref)
      // Ожидание сообщения Greeted от актора Greeter
      replyProbe.expectMessage(Greeted("Santa", underTest.ref))
    }
  }
}
