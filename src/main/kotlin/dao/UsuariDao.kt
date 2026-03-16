package dao

import model.NivellUsuari
import model.Usuari
import java.sql.Connection
import java.sql.ResultSet

public class UsuariDao {
    private val databaseConnection: Connection

    constructor (databaseSource: Connection){
        this.databaseConnection = databaseSource
    }

    /**
     * Funció que es connecta directament amb la BD usant la connexió definida a la classe DataBaseManager
     * @return Retorna el llistat d'usuaris existents a la BD
     * @author RIS
     */
    public fun getAllUsers(): List<Usuari> {
        val usuaris: MutableList<Usuari> = mutableListOf<Usuari>()

        // Definim la consulta SQL que volem llançar a la BD
        val sql = "SELECT * FROM usuari ORDER BY usu_id ASC"

        // Convertim l'String anterior a una sentència SQL executable
        //val stmt = DatabaseManager.connection.createStatement()
        //val stmt = SupabaseManager.connection.createStatement()
        val stmt = databaseConnection.createStatement()

        // Executem la sentència SQL creada
        val rs: ResultSet = stmt.executeQuery(sql)

        while (rs.next()) {
            val nivellString = rs.getString("usu_nivell")
            val nivellEnum = NivellUsuari.fromString(nivellString)

            usuaris.add(
                Usuari(
                    id = rs.getInt("usu_id"),
                    nom = rs.getString("usu_nom"),
                    nivell = nivellEnum
                )
            )
        }

        // Tanquem el ResultSet (dades de resposta de la BD)
        rs.close()
        // Tanquem la connexió amb la BD
        stmt.close()

        return usuaris
    }

    /**
     * Funció que es connecta a la BD usant la connexió definida a dins de DatabaseManager i retorna el llistat de diferents ids que hi ha actualment a la BD
     * @return llistat d'ids que hi ha a la BD
     * @author RIS
     */
    public fun getUserIdList(): List<Int>{
        val userIds: MutableList<Int> = mutableListOf<Int>()

        // Definim la consulta SQL que volem llançar a la BD
        val sql = "SELECT DISTINCT usu_id FROM usuari ORDER BY usu_id ASC"

        // Convertim l'String anterior a una sentència SQL executable
        val stmt = databaseConnection.createStatement()
        // Executem la sentència SQL creada
        val rs: ResultSet = stmt.executeQuery(sql)

        while (rs.next()){
            val id = rs.getInt("usu_id")
            userIds.add(id)
        }

        // Tanquem el ResultSet (dades de resposta de la BD)
        rs.close()
        // Tanquem la connexió amb la BD
        stmt.close()

        return userIds
    }

    /**
     * Funció que es connecta directament a la BD i retorna el primer usuari@ que correspon a l'id rebut.
     * Si no exiteix id, retorna Usuari null
     * @param id de tipus Int
     * @author RIS
     */
    public fun getUserById(id: Int): Usuari?{
        var user: Usuari? = null

        // Definim la consulta SQL que volem llançar a la BD
        val sql = "SELECT * FROM usuari WHERE usu_id = $id ORDER BY usu_nom ASC LIMIT 1" // Afegim LIMIT 1 per si de cas hi ha més d'un usuari amb el mateix id

        // Convertim l'String anterior a una sentència SQL executable
        val stmt = databaseConnection.createStatement()
        // Executem la sentència SQL creada
        val rs: ResultSet = stmt.executeQuery(sql)

        if (rs.next()) {
            val usu_id = rs.getInt("usu_id")
            val usu_nom = rs.getString("usu_nom")
            val usu_nivell: NivellUsuari = NivellUsuari.fromString(rs.getString("usu_nivell"))

            user = Usuari(usu_id, usu_nom, usu_nivell)
        }

        // Tanquem el ResultSet (dades de resposta de la BD)
        rs.close()
        // Tanquem la connexió amb la BD
        stmt.close()

        return user
    }

    /**
     * Funció que es connecta amb la BD i insereix una fila nova amb les dades de l'usuari rebut per paràmetre
     * @param usuari de tipus Usuari amb les dades d'un usuari nou
     * @author RIS
     */
    public fun insertUser(usuari: Usuari): Int{
        var rowCount: Int = 0
        val sql = "INSERT INTO usuari (usu_id, usu_nom, usu_nivell) VALUES (?, ?, ?)"

        // Amb la funció prepareStatement() s'eviten els possibles atacs de SQLInjection
        // 'use' tanca automàticament el PreparedStatement
        databaseConnection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, usuari.getId())
            stmt.setString(2, usuari.getNom())
            stmt.setString(3, usuari.getNivell()) // enum a String

            rowCount = stmt.executeUpdate() // La funció executeUpdate serveix per a modificacions de dades INSERT, UPDATE i DELETE
        }

        return rowCount
    }

    /**
     * Funció que es connecta amb la BD i insereix una fila nova amb les dades de l'usuari rebut per paràmetre
     * @param usuari de tipus Usuari amb les dades d'un usuari nou
     * @return rowcount de files afectades
     * @author RIS
     */
    public fun updateUser(usuari: Usuari): Int{
        var rowCount: Int = 0
        val sql = "UPDATE usuari SET usu_nom = ?, usu_nivell = ? WHERE usu_id = ?"

        // Amb la funció prepareStatement() s'eviten els possibles atacs de SQLInjection
        // 'use' tanca automàticament el PreparedStatement
        databaseConnection.prepareStatement(sql).use { stmt ->
            stmt.setString(1, usuari.getNom())
            stmt.setString(2, usuari.getNivell())
            stmt.setInt(3, usuari.getId())

            rowCount = stmt.executeUpdate() // La funció executeUpdate serveix per a modificacions de dades INSERT, UPDATE i DELETE
        }

        return rowCount
    }

    /**
     * Funció que es connecta amb la BD i elimina l'usuari passat per paràmetre
     * @param usuari de tipus Usuari l'usuari a eliminar
     * @return rowcount de files afectades
     * @author RIS
     */
    public fun deleteUser(usuari: Usuari): Int {
        var rowCount: Int

        val sql = "DELETE FROM usuari WHERE usu_id = ?"

        // Amb la funció prepareStatement() s'eviten els possibles atacs de SQLInjection
        // 'use' tanca automàticament el PreparedStatement
        databaseConnection.prepareStatement(sql).use { stmt ->
            stmt.setInt(1, usuari.getId())
            rowCount = stmt.executeUpdate() // La funció executeUpdate serveix per a modificacions de dades INSERT, UPDATE i DELETE
        }

        return rowCount
    }

    /**
     * Funció que connecta amb la BD i elimina totes les files de la taula
     * @return el nombre de files afectades
     * @author RIS
     */
    public fun deleteAll(): Int {
        var rowCount: Int

        val sql = "DELETE FROM usuari"

        // Amb la funció prepareStatement() s'eviten els possibles atacs de SQLInjection
        // 'use' tanca automàticament el PreparedStatement
        databaseConnection.prepareStatement(sql).use { stmt ->
            rowCount = stmt.executeUpdate() // La funció executeUpdate serveix per a modificacions de dades INSERT, UPDATE i DELETE
        }

        return rowCount
    }
}