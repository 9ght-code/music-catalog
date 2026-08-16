package model

case class Artist(id: Int, name: String)
case class Album(id: Int, title: String, year: Int, artistId: Int)
case class Track(id: Int, title: String, durationSeconds: Int, albumId: Int)
case class Genre(id: Int, name: String)
case class TrackWithGenres(id: Int, title: String, durationSeconds: Int, albumId: Int, genres: List[String])
