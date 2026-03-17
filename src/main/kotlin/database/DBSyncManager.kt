package database

import dao.UsuariDao

/**
 * Aquesta classe estàtica (object) es fa servir per sincronitzar les dades del local amb les del remot
 * Està pensat per a què s'executi just al iniciar i abans de tancar l'aplicació
 */
object DBSyncManager {
    fun syncDatabases() {
        val localDao = UsuariDao(DatabaseManager.getConn())
        val remoteDao = UsuariDao(SupabaseManager.getConn())

        try {
            val remoteUsers = remoteDao.getAllUsers()
            // Purga local i actualitza amb dades remotes
            localDao.deleteAll()
            remoteUsers.forEach { localDao.insertUser(it) }
            println("Sincronització completada: Local actualitzat amb el Núvol.")
        } catch (e: Exception) {
            println("No s'ha pogut sincronitzar: Treballant només amb dades locals.")
        }
    }
}