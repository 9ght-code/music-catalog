package service

import model.TrackWithGenres
import model.Track
import repository.TrackRepositoryTrait

class TrackService(trackRepository: TrackRepositoryTrait):
    def findAllWithGenres(): List[TrackWithGenres] = trackRepository.findAllWithGenres()

    def findById(id: Int): Either[String, Track] =
        trackRepository.findById(id) match
            case Some(track) => Right(track)
            case None => Left("Трек не найден")

        
         
