import scala.io.StdIn.readLine
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import checkPassword.goodEnoughPasswordTask2b

// Задание 2 (Future)
def readPassword(): Future[String] = {
  Future {
    println("Введите пароль:")
    readLine()
  }.flatMap(password =>
    goodEnoughPasswordTask2b(password) match {
      case Left(_) => 
        Future.successful(password)
      case Right(error) =>
        println(s"Ошибка: $error")
        readPassword()
    }
  )
}

@main def task2c(): Unit = {
  println("Задание 2(в):")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val passwordFuture = readPassword()

  passwordFuture.onComplete {
    case Success(password) => 
      println(s"Пароль успешно принят: $password")
      System.exit(0)
    case Failure(exception) => 
      println(s"Произошла ошибка: ${exception.getMessage}")
      System.exit(1)
  }

  Thread.sleep(50000)
}