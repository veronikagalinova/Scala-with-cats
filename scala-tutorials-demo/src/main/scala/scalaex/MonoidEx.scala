package com.github.veronikagalinova
package scalaex

import cats.Monoid
import cats.effect.{IO, IOApp}
import cats.implicits.toFoldableOps

/* https://www.scala-exercises.org/cats/monoid

  Monoid extends the Semigroup type class, adding an empty method to semigroup's combine.
  (combine(x, empty) == combine(empty, x) == x)
  The advantage of using these type class provided methods, rather than the specific ones for each type,
  is that we can compose monoids to allow us to operate on more complex types
*/
object MonoidEx extends IOApp.Simple {

  val emptyString = Monoid[String].empty
  val stringsCombined = Monoid[String].combineAll(List("a", "b", "c")) // "abc"
  val listCombined = Monoid[String].combineAll(List()) // ""
  val mapsCombined = Monoid[Map[String, Int]].combineAll(List(Map("a" -> 1, "b" -> 2), Map("a" -> 3))) // Map("a" -> 4, "b" -> 2)

  // Foldable's foldMap maps over values accumulating the results, using the available Monoid for the type mapped onto
  val mapToString = List(1, 2, 3, 4, 5).foldMap(_.toString) // "12345"
  val mapToIdentity = List(1, 2, 3, 4, 5).foldMap(identity) // 15


  implicit def monoidTuple[A: Monoid, B: Monoid]: Monoid[(A, B)] =
    new Monoid[(A, B)] {
      def combine(x: (A, B), y: (A, B)): (A, B) = {
        val (xa, xb) = x
        val (ya, yb) = y
        (Monoid[A].combine(xa, ya), Monoid[B].combine(xb, yb))
      }

      def empty: (A, B) = (Monoid[A].empty, Monoid[B].empty)
    }


  val tupleCombined = List(1, 2, 3, 4, 5).foldMap(i => (i, i.toString)) // (15,12345)

  override def run: IO[Unit] = IO.println(tupleCombined)
}
