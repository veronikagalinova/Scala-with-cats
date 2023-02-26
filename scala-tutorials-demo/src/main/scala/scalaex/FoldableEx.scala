package com.github.veronikagalinova
package scalaex

import cats.Foldable
import cats.effect.{IO, IOApp}
import cats._
import cats.implicits._

/*
    https://www.scala-exercises.org/cats/foldable
    Foldable type class instances can be defined for data structures that can be folded to a summary value.
 */
object FoldableEx extends IOApp.Simple {

  // foldLeft(fa, b)(f) eagerly folds fa from left-to-right.
  val a = Foldable[List].foldLeft(List(1, 2, 3), 0)(_ + _) // 6
  val b = Foldable[List].foldLeft(List("a", "b", "c"), "")(_ + _)

  // foldRight(fa, b)(f) lazily folds fa from right-to-left.
  val lazyResult =
    Foldable[List].foldRight(List(1, 2, 3), Now(0))((x, rest) => Later(x + rest.value))

  // fold, also called combineAll, combines every value in the foldable using the given Monoid instance.
  val c = Foldable[List].fold(List("a", "b", "c")) // "abc"
  val d = Foldable[List].fold(List(1, 2, 3)) // 6

  // foldMap is similar to fold but maps every A value into B and then combines them using the given Monoid[B] instance.
  val e = Foldable[List].foldMap(List("a", "b", "c"))(_.length) // 3
  val f = Foldable[List].foldMap(List(1, 2, 3))(_.toString) // "abc"

  // foldK is similar to fold but combines every value in the foldable
  // using the given MonoidK[G] instance instead of Monoid[G].
    val g = Foldable[List].foldK(List(List(1, 2), List(3, 4, 5))) // List(1, 2, 3, 4, 5)
  val h = Foldable[List].foldK(List(None, Option("two"), Option("three"))) // Some(two)


  def parseInt(s: String): Option[Int] =
    Either.catchOnly[NumberFormatException](s.toInt).toOption

  val traverseExample1 = Foldable[List].traverse_(List("1", "2", "3"))(parseInt) // Some(())
  val traverseExample2 = Foldable[List].traverse_(List("a", "b", "c"))(parseInt) // None

  val FoldableListOption = Foldable[List].compose[Option]
  val composeEx = FoldableListOption.fold(List(Option(1), Option(2), Option(3), Option(4))) // 10
  override def run: IO[Unit] = IO.println(FoldableListOption.fold(List(Option("1"), Option("2"), None, Option("3")))) // "123"
}
