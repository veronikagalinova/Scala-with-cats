package com.github.veronikagalinova
package scalaex

import cats.Monad
import cats.effect.{IO, IOApp}

/*
  https://www.scala-exercises.org/cats/monad
 */
object MonadEx extends IOApp.Simple{

  // Flatten takes a value in a nested context (eg. F[F[A]] where F is the context) and "joins"
  // the contexts together so that we have a single context (ie. F[A]).
  val a = Option(Option(1)).flatten // Option(1)
  val b = Option(None).flatten      // None
  val c = List(List(1), List(2, 3)).flatten  // List(1,2,3)

  val d = Monad[List].flatMap(List(1, 2, 3))(x => List(x, x)) // List((1,1), (2,2), (3,3))

  // ifM - lifts an if statement into the monadic context
  val e = Monad[Option].ifM(Option(true))(Option("truthy"), Option("falsy")) // Some(truthy)
  val f = Monad[List].ifM(List(true, false, true))(List(1, 2), List(3, 4)) // List(1, 2, 3, 4, 1, 2)

  override def run: IO[Unit] = IO.println(e)
}
