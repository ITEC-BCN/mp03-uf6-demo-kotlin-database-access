package database

import java.sql.Connection
import java.sql.DriverManager
import java.util.Properties

/**
 * Classe estàtica que té com a objectiu crear només una connexió amb la BD desitjada
 * Els mètodes d'aquesta classe s'han de cridar directament sense crear un objecte de la classe.
 * @author RIS
 */
object SupabaseManager {

    /*
    Agafem les dades de configuració de la base de dades remota usant les variables de sistema creades pel build.gradle.kts
    en el moment de compilació del projecte.
    Les variables agafaran els valors definits a dins de l'arxiu secrets.properties que no serà pujat a GitHub.
    Per tant, el projecte només funcionarà allà on tinguem l'arxiu secrets.properties en local.
     */
    private val URL = System.getProperty("SUPABASE_URL") ?: throw IllegalStateException("URL de Supabase no trobada")
    private val USER = System.getProperty("SUPABASE_USER")
    private val PASS = System.getProperty("SUPABASE_PASS")
    private val PORT = System.getProperty("SUPABASE_PORT")

    // La connexió es crea només un cop, quan es crida per primer cop
    public val connection: Connection by lazy {
        val props = Properties().apply {
            setProperty("user", USER)
            setProperty("password", PASS)
            setProperty("port", PORT)
            setProperty("sslmode", "require")
        }
        DriverManager.getConnection(URL, props)
    }
}