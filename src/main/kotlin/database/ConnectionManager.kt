package database

import java.sql.Connection

/**
 * Aquesta classe estàtica (object) fa de gestor de connexions de bases de dades pel projecte
 * Prioritza usar la Supabase remota, però si no hi té accés, usarà la SQLite local.
 */
object ConnectionManager {
    private var connection: Connection? = null
    private var isCloud: Boolean = false

    fun getConnection(): Connection {
        // 0. Si la connexió ja està creada i activa, no la torna a crear
        if (this.connection != null && !this.connection!!.isClosed)
            return this.connection!!

        // 1. Intentem Supabase (Remot)
        return try {
            this.connection = SupabaseManager.getConn() // Mètode que crea la connexió
            this.isCloud = true
            println("Connectat a Supabase (Cloud)")
            this.connection!!
        } catch (e: Exception) {
            // 2. Si falla, anem a SQLite (Local)
            this.connection = DatabaseManager.getConn()
            this.isCloud = false
            println("Sense connexió. Treballant en local (SQLite)")
            this.connection!!
        }
    }

    fun isUsingCloud(): Boolean{
        return this.isCloud
    }
}