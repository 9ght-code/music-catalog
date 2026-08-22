package service

import model.Artist
import repository.ArtistRepository
import repository.ArtistRepositoryTrait

class ArtistService(artistRepository: ArtistRepositoryTrait):

    def validateName(name: String): Boolean =
        if (name.trim.isEmpty) then false
        else if (name.length > 200) then false
        else true

    def findAll(): List[Artist] = artistRepository.findAll()
    
    def findById(id: Int): Option[Artist] = artistRepository.findById(id)

    def create(name: String): Either[String, Artist] =
        if (!validateName(name)) then Left("Некорректное имя")
        else Right(artistRepository.create(name.trim))

    def update(id: Int, name: String): Either[String, Artist] =
        if (!validateName(name)) then Left("Некорректное имя")
        else {
            artistRepository.update(id, name.trim) match 
                case Some(updated) => Right(updated)
                case None => Left("Артист не найден!")
        }

    def delete(id: Int): Boolean = 
        artistRepository.delete(id)

        
