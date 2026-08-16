import java.sql.DriverManager
val CONNECTION_STRING = "jdbc:postgresql://localhost:5432/music_catalog"

@main def run(): Unit = {
    val conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "123456")
    scala.util.Using.resource(conn) {
        connection => {
            val a = ArtistRepository(conn)
            for (artist <- a.findAll()) println(artist)
        }
    }
}