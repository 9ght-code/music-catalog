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

    @cask.get("/tracks/:id")
    def findById(id: Int): cask.Response[ujson.Value] =
        trackService.findById(id) match
            case Right(track) => cask.Response(ujson.Obj(
                "id" -> track.id, 
                "title" -> track.title, 
                "duration_seconds" -> track.durationSeconds, 
                "album_id" -> track.albumId))
                
            case Left(error) => cask.Response(ujson.Obj("error" -> error), statusCode = 404)        
 
    initialize()
