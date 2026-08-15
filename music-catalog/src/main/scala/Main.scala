import java.sql.DriverManager
val CONNECTION_STRING = "jdbc:postgresql://localhost:5432/music_catalog"

@main def run(): Unit = {
    val conn = DriverManager.getConnection(CONNECTION_STRING, "postgres", "123456")
    scala.util.Using.resource(conn) {
        connection => {
            val rs = connection.createStatement().executeQuery("SELECT version();")
            while rs.next() do
                println(rs.getString(1))
        }
    }
}