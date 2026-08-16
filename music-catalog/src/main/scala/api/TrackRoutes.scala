package api

import service.TrackService

class TrackRoutes(trackService: TrackService)(implicit cc: castor.Context, log: cask.Logger) extends cask.Routes:
    @cask.get("/tracks")
    def tracks() =
        trackService.findAllWithGenres().map(t => ujson.Obj(
            "id" -> t.id,
            "title" -> t.title,
            "durationSeconds" -> t.durationSeconds,
            "albumId" -> t.albumId,
            "genres" -> t.genres
        ))
 
    initialize()
