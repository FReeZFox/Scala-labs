package checkPassword

import scala.io.StdIn.readLine
import scala.util.{Try, Success, Failure}

// Задание 2 (Try)
def goodEnoughPasswordTaskTry(password: String): Either[Boolean, String] = {
  val passwordRequirements: Seq[(String, String => Boolean)] = Seq(
    ("Пароль должен содержать минимум 8 символов", _.length >= 8),
    ("Пароль должен содержать минимум 1 латинскую заглавную букву", _.exists(_.isUpper)),
    ("Пароль должен содержать минимум 1 латинскую строчную букву", _.exists(_.isLower)),
    ("Пароль должен содержать минимум 1 цифру", _.exists(_.isDigit)),
    ("Пароль должен содержать минимум 1 специальный символ", 
      _.exists(c => "!@#$%^&*()_+-=[]{}|;:'\",.<>?".contains(c)))
  )

  Try {
    passwordRequirements
    .collect{ case (errorMessage, requirement) if !requirement(password) => Right(errorMessage) }
    .headOption     
    .getOrElse(Left(true))                       
  } match {
    case Success(result) => result
    case Failure(_) => Right("Неизвестная ошибка")
  }
}

@main def CheckPasswordTry(): Unit = {
  println("Задание 2(б):")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val input = readLine()
  val result = goodEnoughPasswordTaskTry(input)

  if (result.isLeft) {
    println("Пароль удовлетворяет всем условиям.")
  } else {
    println(s"Пароль не удовлетворяет условию: ${result.merge}")
  }
}