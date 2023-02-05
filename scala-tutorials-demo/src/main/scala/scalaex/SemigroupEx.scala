package com.github.veronikagalinova
package scalaex

import cats.Semigroup
import cats.effect.{IO, IOApp}
import cats.implicits.catsSyntaxSemigroup

/* https://www.scala-exercises.org/cats/semigroup
  Semigroup - A semigroup for some given type A has a single operation (which we will call combine),
  which takes two values of type A, and returns a value of type A. This operation must be guaranteed
  to be associative.
 */
object SemigroupEx extends IOApp.Simple {

  val integersCombined: Int = Semigroup[Int].combine(1, 2) // 3
  val listsCombined: List[Int] = Semigroup[List[Int]].combine(List(1, 2, 3), List(4, 5, 6)) // List[1,2,3,4,5,6]
  val optionsCombined: Option[Int] = Semigroup[Option[Int]].combine(Option(1), Option(2)) // Option(3)
  val optionCombinedWithNone: Option[Int] = Semigroup[Option[Int]].combine(Option(1), None) // Some(1)

  Semigroup[Int => Int].combine(_ + 1, _ * 10).apply(6) // 67

  override def run: IO[Unit] = {
    IO.println(
      //      combineMaps() // Map(foo -> Map(bar -> 11))
      combineOptions() // Some(3)
    )
  }

  def combineMaps() = {
    val aMap = Map("foo" -> Map("bar" -> 5))
    val anotherMap = Map("foo" -> Map("bar" -> 6))
    Semigroup[Map[String, Map[String, Int]]].combine(aMap, anotherMap)
  }

  def combineOptions() = {
    val one: Option[Int] = Option(1)
    val two: Option[Int] = Option(2)
    val n: Option[Int] = None

    one |+| two |+| n
  }

}
