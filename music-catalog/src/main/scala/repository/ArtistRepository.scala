package repository

import java.sql.Connection
import model.Artist
import scala.collection.mutable.ListBuffer
import scala.util.Using 

class ArtistRepository(conn: Connection):
    def findAll(): List[Artist] =
        Using.resource(conn.createStatement()) {stmt => 
            val rs = stmt.executeQuery("Select id, name from artists")
            val result = ListBuffer.empty[Artist]
            while rs.next() do
                result += Artist(rs.getInt("id"), rs.getString("name"))
            result.toList
            } 
    
    def findById(id: Int): Option[Artist] =
        Using.resource(conn.prepareStatement("Select id, name from artists where id = ?")) {stmt => 
            stmt.setInt(1, id)
            val rs = stmt.executeQuery()
            if rs.next() then Some(Artist(rs.getInt("id"), rs.getString("name"))) else None
            }

