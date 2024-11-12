package checkPassword

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

// Задание 2 (Try)
def goodEnoughPasswordTask2b(password: String): Either[Boolean, String] = {
  Try {
    if (password.length < 8) {
      Right("Пароль должен содержать как минимум 8 символов")
    } else if (!password.exists(_.isUpper)) {
      Right("Пароль должен содержать как минимум 1 латинскую заглавную букву")
    } else if (!password.exists(_.isLower)) {
      Right("Пароль должен содержать как минимум 1 латинскую строчную букву")
    } else if (!password.exists(_.isDigit)) {
      Right("Пароль должен содержать как минимум 1 цифру")
    } else if (!password.exists(c => "!@#$%^&*()_+-=[]{}|;:'\",.<>?".contains(c))) {
      Right("Пароль должен содержать как минимум 1 специальный символ")
    } else {
      Left(true)
    }
  } match {
    case Success(result) => result
    case Failure(_) => Right("Неизвестная ошибка")
  }
}

@main def task2b(): Unit = {
  println("Задание 2(б):")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val input = readLine()
  val result = goodEnoughPasswordTask2b(input)

  if (result.isLeft) {
    println("Пароль удовлетворяет всем условиям.")
  } else {
    println(s"Пароль не соответствует условию: ${result.merge}")
  }
}