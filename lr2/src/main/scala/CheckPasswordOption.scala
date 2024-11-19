import scala.io.StdIn.readLine

// Задание 2 (Option)
// Понимаю что по заданию надо Option, но он ощущается немного лишним
// Без Option возвращается true или false, а с ним Some(True) и Some(false)
// Ну или я неправильно понял как его использовать
def goodEnoughPasswordTaskOption(password: String): Option[Boolean] = {
  Option(
    Seq(
      password.length >= 8,
      password.exists(_.isUpper), 
      password.exists(_.isLower),
      password.exists(_.isDigit),
      password.exists(c => "!@#$%^&*()_+-=[]{}|;:'/\",.<>?".contains(c))
    ).reduce(_ && _)
  )
}

@main def checkPasswordOption(): Unit = {
  println("Задание 2(а)")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val input = readLine()
  val result = goodEnoughPasswordTaskOption(input)

  if (result.contains(true)) {
    println(s"${result}, Пароль удовлетворяет условиям.")
  } else {
    println(s"${result}, Одно из условий нарушено.")
  }
}