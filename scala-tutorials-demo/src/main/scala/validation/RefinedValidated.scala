package com.github.veronikagalinova
package validation

import cats.data.{EitherNel, ValidatedNel}
import cats.implicits._
import cats.effect.{IO, IOApp}
import eu.timepit.refined.api.{Refined, RefinedTypeOps}
import eu.timepit.refined.collection.Contains
import eu.timepit.refined.types.all.NonEmptyString


object RefinedValidated extends IOApp.Simple {

  case class Song(artist: NonEmptyString, title: ContainsG, albumTitle: NonEmptyString)

  // TO DO: Check p4

  //   --------------- Refined + Validated ----------------
  type ContainsG = String Refined Contains['g']

  object ContainsG extends RefinedTypeOps[ContainsG, String]

  /*
    Refined lets us perform runtime validation via Either, which forms a Monad.
    This means validation is done sequentially. It would fail on the first error encountered
    during multiple value validation.
 */
  def p1(a: String, b: String, c: String): IO[Unit] = {
    val result: EitherNel[String, Song] =
      (NonEmptyString.from(a).toEitherNel,
        ContainsG.from(b).toEitherNel,
        NonEmptyString.from(c).toEitherNel)
        .parMapN(Song.apply) // Validated conversion via Parallel
    IO.println(result)
  }

  def p2(a: String, b: String, c: String): IO[Unit] = {
    val result: ValidatedNel[String, Song] =
      (NonEmptyString.from(a).toValidatedNel,
        ContainsG.from(b).toValidatedNel,
        NonEmptyString.from(c).toValidatedNel)
        .mapN(Song.apply)
    IO.println(result)
  }

  //  override def run: IO[Unit] = p1("Acme Band", "gHappy Day", "Songs About Life") // Right(Song(Acme Band,gHappy Day,Songs About Life))

  override def run: IO[Unit] = p2("Acme Band", "Happy Day", "Songs About Life")
}
