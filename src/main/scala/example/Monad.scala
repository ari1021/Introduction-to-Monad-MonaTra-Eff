package example

object Monad {
  def optionMonad(): Unit = {
    val ma: Option[Int] = Some(1)
    val mb: Option[Int] = Some(2)
    val mc: Option[Int] = None

    val res1: Option[Int] = for {
      a <- ma
      b <- mb
    } yield a + b

    val res2: Option[Int] = for {
      a <- ma
      c <- mc
    } yield a + c

    println(res1)
    println(res2)
  }

  def eitherMonad(): Unit = {
    val ma: Either[String, Int] = Right(1)
    val mb: Either[String, Int] = Right(2)
    val mc: Either[String, Int] = Left("error")

    val res1: Either[String, Int] = for {
      a <- ma
      b <- mb
    } yield a + b

    val res2: Either[String, Int] = for {
      a <- ma
      c <- mc
    } yield a + c

    println(res1)
    println(res2)
  }

  def listMonad(): Unit = {
    val ma: List[Int] = List(1, 2)
    val mb: List[Int] = List(3, 4)
    val mc: List[Int] = Nil

    val res1: List[Int] = for {
      a <- ma
      b <- mb
    } yield a + b

    val res2: List[Int] = for {
      a <- ma
      c <- mc
    } yield a + c

    println(res1)
    println(res2)
  }
}
