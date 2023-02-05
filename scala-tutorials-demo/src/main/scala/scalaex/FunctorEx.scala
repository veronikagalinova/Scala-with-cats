package com.github.veronikagalinova
package scalaex

import cats.Functor
import cats.effect.{IO, IOApp}

/*
  https://www.scala-exercises.org/cats/functor

  The Functor category involves a single operation, named map:
  def map[A, B](fa: F[A])(f: A => B): F[B]
 */
object FunctorEx extends IOApp.Simple {

  val listFunctor = Functor[List].map(List("qwer", "adsfg"))(_.length) // List(4,5)

  val optionNonEmpty = Functor[Option].map(Option("Hello"))(_.length) // Some(5)
  val optionEmpty = Functor[Option].map(None: Option[String])(_.length) // None

  // We can use Functor to "lift" a function from A => B to F[A] => F[B]
  val lenOption: Option[String] => Option[Int] = Functor[Option].lift(_.length)

  // fproduct - pairs a value with the result of applying a function to that value
  val source = List("Cats", "is", "awesome")
  val product = Functor[List].fproduct(source)(_.length).toMap

  product.get("Cats").getOrElse(0) // 3
  product.get("is").getOrElse(0) // 2
  product.get("awesome").getOrElse(0) // 7

  // compose - Given any functor F[_] and any functor G[_] we can create a new functor F[G[_]] by composing them:
  val listOpt = Functor[List] compose Functor[Option]
  val listOptComposedResult = listOpt.map(List(Some(1), None, Some(3)))(_ + 1)

  //  override def run: IO[Unit] = IO.println(lenOption(Some("abcd")))
  override def run: IO[Unit] = IO.println(listOptComposedResult)
}
