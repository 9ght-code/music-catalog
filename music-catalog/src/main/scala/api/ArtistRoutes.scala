package api

import service.ArtistService

class ArtistRoutes(artistService: ArtistService)(implicit cc: castor.Context, log: cask.Logger) extends cask.Routes:
    @cask.get("/artists")
    def artists() = artistService.findAll().map(a => ujson.Obj("id" -> a.id, "name" -> a.name))

    @cask.get("/artists/:id")
    def artistById(id: Int): cask.Response[ujson.Value] = 
        artistService.findById(id) match
            case Some(a) => cask.Response(ujson.Obj("id" -> a.id, "name" -> a.name))
            case None    => cask.Response(ujson.Obj("error" -> "Not Found"), statusCode = 404)
        
    initialize()

