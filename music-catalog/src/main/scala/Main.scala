import java.sql.DriverManager
val CONNECTION_STRING = "jdbc:postgresql://localhost:5432/music_catalog"

object Main extends cask.MainRoutes:
    val conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "123456")
    val repo = ArtistRepository(conn)

    @cask.get("/artists")
    def artists() = repo.findAll().map(a => ujson.Obj("id" -> a.id, "name" -> a.name))
    initialize()