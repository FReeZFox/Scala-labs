trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object optionFunctor extends Functor[Option] {
  def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
    case Some(x) => Some(f(x))
    case None => None
  }
}

trait Monad[M[_]] {
  def unit[A](a: A): M[A]
  
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
}

object optionMonad extends Monad[Option] {
  def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = ma match {
    case Some(x) => f(x)
    case None => None
  }
  def unit[A](a: A): Option[A] = Some(a)
}

@main def monadAndFunctor(): Unit = {
    val someOption: Option[Int] = Some(5)
    val noneOption: Option[Int] = None

    println(optionFunctor.map(someOption)(_ * 2)) // Some(10)
    println(optionFunctor.map(noneOption)(_ * 2)) // None

    println(optionMonad.flatMap(someOption)(x => Some(x + 3))) // Some(8)
    println(optionMonad.flatMap(noneOption)(x => Some(x + 3))) // None
}