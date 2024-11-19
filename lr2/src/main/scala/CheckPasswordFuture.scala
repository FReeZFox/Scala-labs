import scala.io.StdIn.readLine
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Await}
import scala.concurrent.duration.Duration
import checkPassword.goodEnoughPasswordTaskTry

// Задание 2 (Future)
// Пробовал делать не с flatMap, а с map, но в таком случае не получается
// нормально сделать рекурсивный вызов
def readPassword(): Future[String] = {
  Future {
    print("\nведите пароль: ")
    readLine()
  }.flatMap(password =>
    goodEnoughPasswordTaskTry(password) match {
      case Left(_) => Future.successful(password)
      case Right(errorMessage) =>
        println(s"Ошибка: $errorMessage")
        readPassword()
    }
  )
}

@main def checkPasswordFuture(): Unit = {
  println("Задание 2(в):")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val passwordFuture = readPassword()
  val password = Await.result(passwordFuture, Duration.Inf)
  println(s"Пароль успешно принят: $password")
}