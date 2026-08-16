package service

import model.TrackWithGenres
import repository.TrackRepository

class TrackService(trackRepository: TrackRepository):
    def findAllWithGenres(): List[TrackWithGenres] = trackRepository.findAllWithGenres()
