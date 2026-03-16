package dao

import model.Usuari

/**
 * Afegim la capa de la interfície IUsuariDao dins de projecte, UsuariController ja no
 * està "casat" amb una base de dades específica. Ara és com un endoll universal:
 * hi pots connectar un UsuariDao que vagi contra SQLite, un que vagi contra Supabase,
 * o fins i tot un que guardi dades en un fitxer de text.
 * El controlador no s'adonarà del canvi perquè sempre crida als mateixos mètodes.
 */
interface IUsuariDao {
    /**
     * Obté la llista completa d'usuaris de la base de dades.
     */
    fun getAllUsers(): List<Usuari>

    /**
     * Retorna una llista amb només els IDs de tots els usuaris.
     */
    fun getUserIdList(): List<Int>

    /**
     * Cerca un usuari específic pel seu ID.
     * Retorna null si no es troba.
     */
    fun getUserById(id: Int): Usuari?

    /**
     * Insereix un nou usuari.
     * @return El nombre de files afectades (normalment 1).
     */
    fun insertUser(u: Usuari): Int

    /**
     * Actualitza les dades d'un usuari existent.
     * @return El nombre de files afectades.
     */
    fun updateUser(u: Usuari): Int

    /**
     * Elimina un usuari de la base de dades per la seva instància.
     * @return El nombre de files afectades.
     */
    fun deleteUser(u: Usuari): Int

    /**
     * Esborra tots els registres de la taula d'usuaris.
     * @return El nombre de files eliminades.
     */
    fun deleteAll(): Int
}