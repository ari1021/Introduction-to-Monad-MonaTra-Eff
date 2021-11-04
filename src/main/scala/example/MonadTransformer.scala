package example

import cats.data.EitherT
import cats.data.OptionT

object MonadTransformer {
  def twoMonadTransform(): Unit = {
    val ma: Option[Int] = Some(1)
    val mb: Either[String, Int] = Right(2)

    // Option[Either[String, Int]]
    val res: EitherT[Option, String, Int] = for {
      a <- EitherT.liftF(ma)
      b <- EitherT.fromEither[Option](mb)
    } yield a + b

    println(res)
    println(res.value)
  }

  def threeMonadTransform(): Unit = {
    val ma: Option[Int] = Some(1)
    val mb: Either[String, Int] = Right(2)
    val mc: List[Int] = List(3, 4)

    // List[Either[String, A]]
    type EitherTListStringOrA[A] = EitherT[List, String, A]
    // List[Either[String, Option[Int]]]
    val res: OptionT[EitherTListStringOrA, Int] = for {
      a <- OptionT.fromOption[EitherTListStringOrA](ma)
      b <- {
        val le: EitherTListStringOrA[Int] = EitherT.fromEither[List](mb)
        OptionT.liftF(le)
      }
      c <- {
        val le: EitherTListStringOrA[Int] = EitherT.liftF(mc)
        OptionT.liftF(le)
      }
    } yield a + b + c

    println(res)
    println(res.value)
    println(res.value.value)
  }
}
