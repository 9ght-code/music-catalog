package mock

import repository.ArtistRepositoryTrait
import model.Artist

class MockArtistRepository extends ArtistRepositoryTrait {
    var artists = List(
        Artist(1, "Queen"),
        Artist(2, "Metallica")
    )

    def findAll(): List[Artist] = artists

    def create(name: String): Artist = 
        val newArtist = Artist(artists.length + 1, name)
        artists = artists :+ newArtist
        newArtist

    def delete(id: Int): Boolean =
        val before = artists.length
        artists = artists.filterNot(_.id == id)
        artists.length < before

    def findById(id: Int): Option[Artist] = artists.find(_.id == id)

    def update(id: Int, name: String): Option[Artist] = 
        artists = artists.map(a => if a.id == id then a.copy(name = name) else a)
        findById(id)
}
