package repository

import java.sql.Connection
import model.Artist
import scala.collection.mutable.ListBuffer
import scala.util.Using 

class ArtistRepository(conn: Connection) extends ArtistRepositoryTrait:
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

    def create(name: String): Artist =
        Using.resource(conn.prepareStatement("Insert into artists (name) values (?) returning id, name")) {stmt =>
            stmt.setString(1, name)
            val rs = stmt.executeQuery()
            rs.next()
            Artist(rs.getInt("id"), rs.getString("name"))          
            }

    def update(id: Int, name: String): Option[Artist] =
        Using.resource(conn.prepareStatement("Update artists set name = ? where id = ? returning id, name")) {stmt =>
            stmt.setString(1, name)
            stmt.setInt(2, id)

            val rs = stmt.executeQuery()
            if rs.next() then Some(Artist(rs.getInt("id"), rs.getString("name"))) 
            else None        
            }

    def delete(id: Int): Boolean =
        Using.resource(conn.prepareStatement("delete from artists where id = ?")) {stmt =>
            stmt.setInt(1, id)
            stmt.executeUpdate() > 0
            }

