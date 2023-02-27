package com.github.veronikagalinova
package scalaex

import cats.effect.{IO, IOApp}
import cats.{Foldable, Semigroup}
import cats.data.{NonEmptyList, OneAnd, Validated, ValidatedNel}
import cats.implicits._

/*
  https://www.scala-exercises.org/cats/traverse
 */
object TraverseEx extends IOApp.Simple {

  // traverse says given a collection of data, and a function that takes a piece of data and returns an
  // effectful value, it will traverse the collection, applying the function and aggregating the effectful values
  // (in a List) as it goes.

  // if any string fail to parse the entire traversal is considered a failure
  def parseIntEither(s: String): Either[NumberFormatException, Int] =
    Either.catchOnly[NumberFormatException](s.toInt)

  // even if one bad parse is hit, it will continue trying to parse the others, accumulating any and all errors as it goes
  def parseIntValidated(s: String): ValidatedNel[NumberFormatException, Int] =
    Validated.catchOnly[NumberFormatException](s.toInt).toValidatedNel

  val intTraverse = List("1", "2", "3").traverse(parseIntEither) // Right(List(1,2,3))

  val eitherTraverse = List("1", "abc", "3").traverse(parseIntEither).isLeft // true


  // sequencing
  val a = List(Option(1), Option(2), Option(3)).sequence // Some(List(1,2,3))
  val b = List(Option(1), None, Option(3)).traverse(identity) // None
  val c = List(Option(1), None, Option(3)).sequence // None


  // using methods of Foldable (superclass of Traverse) - provides traverse_ and sequence_ methods that do the same
  // thing as traverse and sequence but ignores any value produced along the way, returning Unit at the end.
  val g = Foldable[List].sequence_(List(Option(1), Option(2))) // None or Some(value = ())
  val h = List(Option(1), None, Option(3)).sequence_

//  override def run: IO[Unit] = IO.println(List("1", "2", "3").traverse(parseIntValidated).isValid)
  override def run: IO[Unit] = IO.println(c)
}
