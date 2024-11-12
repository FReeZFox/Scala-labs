file:///D:/Политех/5%20семестр/ФиЛП%20(Функциональное%20и%20логическое%20программирование)/LR2/lr2/src/main/scala/task3.scala
### scala.MatchError: TypeDef(B,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree)) (of class dotty.tools.dotc.ast.Trees$TypeDef)

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 360
uri: file:///D:/Политех/5%20семестр/ФиЛП%20(Функциональное%20и%20логическое%20программирование)/LR2/lr2/src/main/scala/task3.scala
text:
```scala
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object optionFunctor extends Functor[Option] {
  def map[A, B](fa: Option[A])(f: A => B): Option[B] = fa match {
    case Some(x) => Some(f(x))
    case None => None
  }
}

trait Monad[M[_]] extends Functor[M] {
  def unit[A](a: A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  ..@@def map[A, B](ma: M[A])(f: A => B): M[B] = flatMap(ma)(a => unit(f(a)))
}

object optionMonad extends Monad[Option] {
  def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = ma match {
    case Some(x) => f(x)
    case None => None
  }
  def unit[A](a: A): Option[A] = Some(a)
}

@main def task3(): Unit = {
    val someOption: Option[Int] = Some(5)
    val noneOption: Option[Int] = None

    println(optionFunctor.map(someOption)(_ * 2)) // Some(10)
    println(optionFunctor.map(noneOption)(_ * 2)) // None

    println(optionMonad.flatMap(someOption)(x => Some(x + 3))) // Some(8)
    println(optionMonad.flatMap(noneOption)(x => Some(x + 3))) // None
}
```



#### Error stacktrace:

```
dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents$$anonfun$2(KeywordsCompletions.scala:218)
	scala.Option.map(Option.scala:242)
	dotty.tools.pc.completions.KeywordsCompletions$.checkTemplateForNewParents(KeywordsCompletions.scala:215)
	dotty.tools.pc.completions.KeywordsCompletions$.contribute(KeywordsCompletions.scala:44)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:122)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:90)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

scala.MatchError: TypeDef(B,TypeBoundsTree(EmptyTree,EmptyTree,EmptyTree)) (of class dotty.tools.dotc.ast.Trees$TypeDef)