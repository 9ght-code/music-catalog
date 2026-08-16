package repository

import model.TrackWithGenres
import java.sql.Connection
import scala.collection.mutable.ListBuffer
import scala.util.Using

val query = """SELECT t.id, t.title, t.duration_seconds, t.album_id,
       string_agg(g.name, ', ' ORDER BY g.name) AS genres
FROM tracks t
JOIN track_genres tg ON tg.track_id = t.id
JOIN genres g ON g.id = tg.genre_id
GROUP BY t.id

"""

class TrackRepository(conn: Connection):
    def findAllWithGenres(): List[TrackWithGenres] = 
        Using.resource(conn.createStatement()) {stmt => 
            val rs = stmt.executeQuery(query)
            val result = ListBuffer.empty[TrackWithGenres]

            while rs.next() do
                val genres = rs.getString("genres").split(", ").toList
                result += TrackWithGenres(rs.getInt("id"), rs.getString("title"), rs.getInt("duration_seconds"), rs.getInt("album_id"), genres)
            result.toList
            
            }
