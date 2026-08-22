package repository

import model.Artist

trait ArtistRepositoryTrait {
    def findAll(): List[Artist]
    def findById(id: Int): Option[Artist]
    def create(name: String): Artist
    def update(id: Int, name: String): Option[Artist]
    def delete(id: Int): Boolean 
}
