package com.github.veronikagalinova
package validation

import cats.implicits._
import cats.effect.{IO, IOApp}
import eu.timepit.refined.api.RefType.refinedRefType
import eu.timepit.refined.api.{RefinedTypeOps}
import eu.timepit.refined.auto._
import eu.timepit.refined.types.string.NonEmptyString


object Refined extends IOApp.Simple {

  // ----------------- Refinement Types -------------------

  type ArtistNameR = NonEmptyString
  object ArtistNameR extends RefinedTypeOps[ArtistNameR, String]

  type SongTitleR = NonEmptyString
  object SongTitleR extends RefinedTypeOps[SongTitleR, String]

  type AlbumTitleR = NonEmptyString
  object AlbumTitleR extends RefinedTypeOps[SongTitleR, String]

  case class Song(artist: ArtistNameR, title: SongTitleR, albumTitle: AlbumTitleR)

  def showSong(artist: ArtistNameR, title: SongTitleR, albumTitle: AlbumTitleR): String =
    s"""
      Song: ${title.value}. played by ${artist.value} as part of album ${albumTitle.value}.
     """

  override def run: IO[Unit] = IO.println(showSong("Acme Band", "Happy Day", "Songs About Life"))
//  override def run: IO[Unit] = IO.println(showSong("", "Happy Day", "Songs About Life")) // COMPILE time exception

}
