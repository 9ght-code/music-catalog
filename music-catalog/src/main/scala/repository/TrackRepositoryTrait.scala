package repository

import model.TrackWithGenres
import model.Track

trait TrackRepositoryTrait {
  def findAllWithGenres(): List[TrackWithGenres]
  def findById(id: Int): Option[Track]
}
