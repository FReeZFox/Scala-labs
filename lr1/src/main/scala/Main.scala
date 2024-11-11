// Задание 1
@main def hello() =
  println("hello world")

// Задание 2
def printHelloNTimes(n: Int): Unit = {
  (0 until n).foreach(i =>
    val x = if (i % 2 == 0) i else n - i
    println(s"hello $x")
  )
}

// Задание 3(а)
def splitByIndex(collection: Seq[Int]): (Seq[Int], Seq[Int]) = {
  val evens = collection.zipWithIndex.filter(p => p._2 % 2 == 0 ).map(p => p._1)
  val odds = collection.zipWithIndex.filter(p => p._2 % 2 != 0 ).map(p => p._1)
  (evens, odds)
}

// Задание 3(а)
def findMax(collection: Seq[Int]): Int = {
  collection.reduce((x, y) => if (x > y) x else y)
}

// Задание 5
def patternMatching(num: Int): String = num match {
  case n if n % 2 == 0  => "Четное число"
  case n if n % 2 != 0  => "Нечетное число"
}

// Задание 6
def compose[A, B, C](f: A => B, g: B => C): A => C = {
  (x: A) => g(f(x))
}

@main def main(): Unit = {
  // Задание 2
  println("Задание 2:")
  printHelloNTimes(5)

  // Задание 3(а)
  println("\nЗадание 3(а):")
  val (evens, odds) = splitByIndex(Seq(1, 2, 3, 4, 5, 6))
  println(s"Четные индексы: $evens, Нечетные индексы: $odds")

  // Задание 3(б)
  println("\nЗадание 3(б):")
  val maxElement = findMax(Seq(-10, 28, 6, 0, 20, 76, 20))
  println(s"Максимальный элемент: $maxElement")

  // Задание 4
  println("\nЗадание 4:")
  val newFunctionPrintHello: Int => Unit = printHelloNTimes
  println(newFunctionPrintHello)
  /*Вывод переменной показал ссылку на функцию. А далее функция по этой переменной будет успешно вызвана
  что говорит о том, в Scala переменные могут хранить функции как значения.*/
  newFunctionPrintHello(5)

  // Задание 5
  println("\nЗадание 5:")
  println(s"6 - ${patternMatching(6)}")
  println(s"3 - ${patternMatching(3)}")
  println(s"-5 - ${patternMatching(-5)}")
  println(s"0 - ${patternMatching(0)}")

  // Задание 6
  println("\nЗадание 6:")
  val composedFunction = compose(splitByIndex, (res: (Seq[Int], Seq[Int])) => findMax(res._1))
  val collection = Seq(10, 20, 30, 70, 50, 40, 30)
  val maxEvenIndex = composedFunction(collection)
  println(s"Максимальный элемент среди четных индексов: $maxEvenIndex")
}