import scala.io.StdIn.readLine

// Задание 2 (Option)
def goodEnoughPasswordTask2a(password: String): Boolean = {
  val hasUpperCase = password.exists(_.isUpper)
  val hasLowerCase = password.exists(_.isLower)
  val hasDigit = password.exists(_.isDigit)
  val hasSpecialChar = password.exists(c => "!@#$%^&*()_+-=[]{}|;:'/\",.<>?".contains(c))
  
  password.length >= 8 && hasUpperCase && hasLowerCase && hasDigit && hasSpecialChar
}

@main def task2a(): Unit = {
  println("Задание 2(а)")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val input = readLine()
  val result = goodEnoughPasswordTask2a(input)
  if (result) {
    println(s"${result}, Пароль удовлетворяет условиям.")
  } else {
    println(s"${result}, Одно из условий нарушено.")
  }
}