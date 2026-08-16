import java.sql.DriverManager

import api.*
import service.*
import repository.*
 
val CONNECTION_STRING = "jdbc:postgresql://localhost:5432/music_catalog"
 
object Main extends cask.Main:
    val conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "123456")
 

    val artistRepository = ArtistRepository(conn)
    val trackRepository  = TrackRepository(conn)
 
    val artistService = ArtistService(artistRepository)
    val trackService  = TrackService(trackRepository)
 
    val allRoutes = Seq(
        ArtistRoutes(artistService),
        TrackRoutes(trackService)
    )
