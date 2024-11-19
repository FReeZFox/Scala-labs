// Задание 1
def integralCalculation(f: Double => Double, l: Double, r: Double, steps: Int): Double = {
  val stepSize = (r - l) / steps

  (0 until steps)
  .map(i => l + i * stepSize)
  .map(x => f(x) * stepSize)
  .reduce(_ + _)
}

@main def Integral(): Unit = {
  // Задание 1
  println("Задание 1:")
  val result = integralCalculation(x => x * x, 0, 10, 1000)
  println(s"Результат: $result")  // Ожидаемый результат для x^2 от 0 до 10: ~333.33
}