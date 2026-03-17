package database

import java.sql.Connection
import java.sql.DriverManager

/**
 * Classe estàtica que té com a objectiu crear només una connexió amb la BD desitjada
 * Els mètodes d'aquesta classe s'han de cridar directament sense crear un objecte de la classe.
 * @author RIS
 */
object DatabaseManager {
    private const val DATABASE_FILE = "data/usuaris.db"

    // La connexió es crea només un cop, quan es crida per primer cop
    private val connection: Connection by lazy {
        DriverManager.getConnection("jdbc:sqlite:$DATABASE_FILE")
    }

    public fun getConn(): Connection{
        return this.connection
    }
}