package service

import model.Artist
import repository.ArtistRepository

class ArtistService(artistRepository: ArtistRepository):
    def findAll(): List[Artist] = artistRepository.findAll()
    
    def findById(id: Int): Option[Artist] = artistRepository.findById(id)