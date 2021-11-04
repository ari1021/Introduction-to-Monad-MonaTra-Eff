package example

import org.atnos.eff.Eff
import org.atnos.eff.Fx
import org.atnos.eff.all._
import org.atnos.eff.syntax.all._
import org.atnos.eff.|=

object ExtensibleEffects {
  def twoMonad(): Unit = {
    val ma: Option[Int] = Some(1)
    val mb: Either[String, Int] = Right(2)

    type EitherString[A] = Either[String, A]
    // declare effects that are used to run program
    type Stack = Fx.fx2[Option, EitherString]

    type _eitherString[R] = EitherString |= R

    // "R: _option : _eitherString" is the constraint for program to use "option" and "eitherString" effects.
    // program[R: _option : _eitherString] is equal to program[R](implicit o: _option[R])(implicit e: _eitherString[R])
    def program[R: _option : _eitherString]: Eff[R, Int] =
      for {
        a <- fromOption(ma)
        b <- fromEither(mb)
      } yield a + b

    val res1: Option[Either[String, Int]] =
      program[Stack]
        .runEither
        .runOption
        .run

    val res2: Either[String, Option[Int]] =
      program[Stack]
        .runOption
        .runEither
        .run

    println(res1)
    println(res2)
  }

  def threeMonad(): Unit = {
    val ma: Option[Int] = Some(1)
    val mb: Either[String, Int] = Right(2)
    val mc: List[Int] = List(3, 4)

    type EitherString[A] = Either[String, A]
    type Stack = Fx.fx3[Option, EitherString, List]

    type _eitherString[R] = EitherString |= R

    def program[R: _option : _eitherString : _list]: Eff[R, Int] =
      for {
        a <- fromOption(ma)
        b <- fromEither(mb)
        c <- fromList(mc)
      } yield a + b + c

    val res1: List[Either[String, Option[Int]]] =
      program[Stack]
        .runOption
        .runEither
        .runList
        .run

    val res2: List[Option[Either[String, Int]]] =
      program[Stack]
        .runEither
        .runOption
        .runList
        .run

    val res3: Option[Either[String, List[Int]]] =
      program[Stack]
        .runList
        .runEither
        .runOption
        .run

    println(res1)
    println(res2)
    println(res3)
  }
}
