package api

import service.ArtistService
import mappers.*

class ArtistRoutes(artistService: ArtistService)(implicit cc: castor.Context, log: cask.Logger) extends cask.Routes:
    @cask.get("/artists")
    def artists() = artistService.findAll().map(a => a.toObj)

    @cask.get("/artists/:id")
    def artistById(id: Int): cask.Response[ujson.Value] = 
        artistService.findById(id) match
            case Some(a) => cask.Response(a.toObj)
            case None    => cask.Response(ujson.Obj("error" -> "Not Found"), statusCode = 404)


    @cask.postJson("/artists")
    def create(name: String): cask.Response[ujson.Value] =
        artistService.create(name) match
            case Right(artist) => cask.Response(artist.toObj)
            case Left(error) => cask.Response(ujson.Obj("error" -> error), statusCode = 401)

    @cask.route("/artists/:id", methods = Seq("put"))
    def update(id: Int, request: cask.Request): cask.Response[ujson.Value] =
        val body = ujson.read(request.text())
        val name = body("name").str

        artistService.update(id, name) match
            case Right(artist) => cask.Response(artist.toObj)
            case Left(error) => cask.Response(ujson.Obj("error" -> error), statusCode = 404)

    @cask.route("/artists/:id", methods = Seq("delete"))
    def delete(id: Int): cask.Response[ujson.Value] =
        artistService.delete(id) match
            case true => cask.Response(ujson.Obj("message" -> "deleted"))
            case false => cask.Response(ujson.Obj("error" -> "couldn't delete"), statusCode = 404)
        
    initialize()

