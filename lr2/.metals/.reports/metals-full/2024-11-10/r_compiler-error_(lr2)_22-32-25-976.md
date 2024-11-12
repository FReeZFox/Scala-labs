file:///D:/Политех/5%20семестр/ФиЛП%20(Функциональное%20и%20логическое%20программирование)/LR2/lr2/src/main/scala/task2b.scala
### java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/danch/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/util/Either.scala

occurred in the presentation compiler.

presentation compiler configuration:


action parameters:
offset: 1875
uri: file:///D:/Политех/5%20семестр/ФиЛП%20(Функциональное%20и%20логическое%20программирование)/LR2/lr2/src/main/scala/task2b.scala
text:
```scala
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

/*
def emulatePasswordInputTask2b(): Unit = {
  while (true) {
    println("\nВведите пароль:")
    val input = readLine()

    if (input == "0") {
      println("Завершение программы.")
      return
    }

    val result = goodEnoughPasswordTask2b(input)

    if (result.left.get) {
        println(s"$result.left.get}, Пароль удовлетворяет всем условиям.")
    } else if (!result.left.get) {
        println(s"${result.left.get}, Неизвестная ошибка.")
    } else { 
        println(s"Пароль не соответствует условию: ${result.right.get}")
    }
  }
}
*/

@main def task2b(): Unit = {
  println("Задание 2(б):")
  println("Минимум: 8 символов, 1 латинская строчная буква, 1 латинская заглавная буква, 1 специальный символ, 1 цифра")

  val input = readLine()
  val result = goodEnoughPasswordTask2b(input)

  if (result.left.get) {
    println("Пароль удовлетворяет всем условиям!")
  } else {
    println(s"Пароль не соответствует условиям: ${result.m@@}")
  }
}
```



#### Error stacktrace:

```
java.base/sun.nio.fs.WindowsPathParser.normalize(WindowsPathParser.java:182)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:153)
	java.base/sun.nio.fs.WindowsPathParser.parse(WindowsPathParser.java:77)
	java.base/sun.nio.fs.WindowsPath.parse(WindowsPath.java:92)
	java.base/sun.nio.fs.WindowsFileSystem.getPath(WindowsFileSystem.java:232)
	java.base/java.nio.file.Path.of(Path.java:147)
	java.base/java.nio.file.Paths.get(Paths.java:69)
	scala.meta.io.AbsolutePath$.apply(AbsolutePath.scala:58)
	scala.meta.internal.metals.MetalsSymbolSearch.$anonfun$definitionSourceToplevels$2(MetalsSymbolSearch.scala:70)
	scala.Option.map(Option.scala:242)
	scala.meta.internal.metals.MetalsSymbolSearch.definitionSourceToplevels(MetalsSymbolSearch.scala:69)
	dotty.tools.pc.completions.CaseKeywordCompletion$.dotty$tools$pc$completions$CaseKeywordCompletion$$$sortSubclasses(MatchCaseCompletions.scala:325)
	dotty.tools.pc.completions.CaseKeywordCompletion$.matchContribute(MatchCaseCompletions.scala:275)
	dotty.tools.pc.completions.Completions.advancedCompletions(Completions.scala:346)
	dotty.tools.pc.completions.Completions.completions(Completions.scala:118)
	dotty.tools.pc.completions.CompletionProvider.completions(CompletionProvider.scala:90)
	dotty.tools.pc.ScalaPresentationCompiler.complete$$anonfun$1(ScalaPresentationCompiler.scala:146)
```
#### Short summary: 

java.nio.file.InvalidPathException: Illegal char <:> at index 3: jar:file:///C:/Users/danch/AppData/Local/Coursier/cache/v1/https/repo1.maven.org/maven2/org/scala-lang/scala-library/2.13.14/scala-library-2.13.14-sources.jar!/scala/util/Either.scala