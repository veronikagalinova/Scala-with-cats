package com.github.veronikagalinova

import cats.MonadError
import cats.effect.{IO, IOApp}
import cats.syntax.applicative._
import cats.syntax.applicativeError._
import cats.instances.either._

import scala.util.Try

object ScalaWithCats extends IOApp.Simple{

  type ErrorOr[A] = Either[String, A]
  val monadError = MonadError[ErrorOr, String]

  val success = 42.pure[ErrorOr] // Right(42)
  // Lift an error into the `F` context:
  val failure = monadError.raiseError("Badness") // Left("Badness")

  // handleErrorWith   // Handle an error, potentially recovering from it:
  val x = monadError.handleErrorWith(failure) {
    case "Badness" => monadError.pure("It's ok")
    case _ => monadError.raiseError("It;s not ok")
  }


  // ensure - implement filter like behaviour
  val y = monadError.ensure(success)("Number too low!")(_ > 1000) // Left(Number too low!)


  // When passed an age greater than or equal to 18 it should return that value as a success.
  // Otherwise it should return a error represented as an IllegalArgumentException.
  def validateAdult[F[_]](age: Int)(implicit me: MonadError[F, Throwable]): F[Int] =
    if (age >= 18) age.pure[F]
    else new IllegalArgumentException("Age must be greater than or equal to 18").raiseError

  val validAdult = validateAdult[Try](18) // Try[Int] = Success(18)
  val invalidAdult = validateAdult[Try](8) // Try[Int] = Failure(java.lang.IllegalArgumentException: Age must be greater than or equal to 18)

  type ExceptionOr[A] = Either[Throwable, A]
  validateAdult[ExceptionOr](-1) // ExceptionOr[Int] = Left(ava.lang.IllegalArgumentException: Age must be greater than or equal to 18)
  override def run: IO[Unit] = IO.println(y)


}
