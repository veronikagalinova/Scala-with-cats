package com.github.veronikagalinova
package scalaex

import cats.{Applicative, Monad}
import cats.effect.{IO, IOApp}

import scala.concurrent.Future

/*
  Applicative extends Apply by adding a single method, pure
  This method takes any value and returns the value in the context of the functor.
  https://stackoverflow.com/questions/19880207/when-and-why-should-one-use-applicative-functors-in-scala

  Picking the least powerful tool that will solve your problem is a tremendously powerful principle.
  Sometimes you really do need monadic composition

  Applicative functor is a functor for applying a special value (value in category) to a lifted function.

  https://bartoszmilewski.com/2017/02/06/applicative-functors/

  https://softwaremill.com/applicative-functor/

  https://www.adit.io/posts/2013-04-17-functors,_applicatives,_and_monads_in_pictures.html
 */
object ApplicativeEx extends IOApp.Simple {

  val optionApplicative = Applicative[Option].pure(1) // Some(2)
  val listApplicative = Applicative[List].pure(1) // List(1)

  val composedListOptionApplicative = (Applicative[List] compose Applicative[Option]).pure(1)

  val a = Monad[Option].pure(1)
  val b = Applicative[Option].pure(1)
  override def run: IO[Unit] = IO.println(b)

  // All monads are also applicatives. We can use them when we have independent flow

  // known as <*> in Haskell). The ap() function allows us to get a wrapped function and fuse it with a wrapped value

}
