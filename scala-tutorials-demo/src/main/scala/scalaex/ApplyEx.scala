package com.github.veronikagalinova
package scalaex

import cats.Apply
import cats.effect.{IO, IOApp}

/*
    https://www.scala-exercises.org/cats/apply
    F[A => B]
    Weaker version of Applicative[F]; has apply but not pure.
 */
object ApplyEx extends IOApp.Simple {

  // Map
  val intToString: Int => String = _.toString
  val double: Int => Int = _ * 2
  val addTwo: Int => Int = _ + 2

  val a = Apply[Option].map(Some(1))(intToString) // Some("1")
  val b = Apply[Option].map(Some(1))(double) // Some(2)
  val c = Apply[Option].map(None)(addTwo) // None


  // compose
  val listOpt = Apply[List] compose Apply[Option]
  val plusOne = (x: Int) => x + 1

  // ap - Given a value and a function in the Apply context, applies the function to the value.
  val a1 = Apply[Option].ap(Some(intToString))(Some(1)) // Some("1")
  val a2 = Apply[Option].ap(Some(double))(Some(2))     // Some(4)
  val a3 = Apply[Option].ap(Some(double))(None)        // None

  // ap2..ap22 if any of the arguments of this example is None, the final result is None as well.

    override def run: IO[Unit] = IO.println(listOpt.ap(List(Some(plusOne)))(List(Some(1), None, Some(3)))) // List(Some(2), None, Some(4))
}
