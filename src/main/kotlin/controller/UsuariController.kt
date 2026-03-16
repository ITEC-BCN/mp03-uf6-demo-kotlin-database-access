package org.example.controllers

import dao.IUsuariDao
import dao.UsuariDao
import model.Usuari

/*
TODO:
6. A dins de UsuariDao, afegir el tema de use per tancar bé les connexions o usar try-catch-finally?
7. Permetre que es pugui treballar usant dades remotes o locals si no hi ha connexió?
 */

public class UsuariController{
    private val usuariDao: IUsuariDao

    /**
     * Constructor de la classe que rep injecció de dependències
     * amb el paràmetre usuariDao que vindrà definit des del Main i ens indica si
     * s'usarà una connexió de BD local o remota
     * @author RIS
     */
    constructor(usuariDao: IUsuariDao) {
        this.usuariDao = usuariDao
    }

    /**
     * Funció del controller que retorna una List no modificable amb totes les dades de tots els Usuaris de la BD
     * @author RIS
     */
    public fun getUsuaris(): List<Usuari> {
        val llistaUsuaris: List<Usuari> = usuariDao.getAllUsers()
        return llistaUsuaris
    }

    /**
     * Funció de controller que retorna el llistat de ids d'Usuari existents dins de la BD
     * @return ids d'Usuari existents dins de la BD
     * @author RIS
     */
    public fun idsUsuari(): List<Int> {
        var idsUsuariActuals: List<Int> = usuariDao.getUserIdList()

        return idsUsuariActuals
    }

    /**
     * Funció del controller que intenta buscar l'usuari corresponent a un id
     * @param idUsuari de tipus Int
     * @return Un Usuari o null
     * @author RIS
     */
    fun getUsuariById(idUsuari: Int): Usuari? {
        val user: Usuari? = usuariDao.getUserById(idUsuari)
        return user
    }

    /**
     * Funció del controller que insereix l'usuari rebut per paràmetre dins de la BD a través d'UsuariDao
     * @author RIS
     */
    public fun inserirUsuari(usuari: Usuari): Int{
        val filesAfectades: Int = usuariDao.insertUser(usuari)
        return filesAfectades
    }

    /**
     * Funció del controller que actualitza l'usuari rebut per paràmetre dins de la BD a través d'UsuariDao
     * @author RIS
     */
    public fun modificarUsuari(usuari: Usuari): Int {
        val filesAfectades: Int = usuariDao.updateUser(usuari)
        return filesAfectades
    }

    /**
     * Funció de controller que envia un Usuari a eliminar
     * @param usuari a eliminar
     * @return rowcount de files afectades
     * @author RIS
     */
    public fun eliminarUsuari(usuari: Usuari): Int {
        val filesAfectades: Int = usuariDao.deleteUser(usuari)
        return filesAfectades
    }

    /**
     * Funció del controller que elimina totes les files de la taula usuaris
     * @return retorna el nombre de files esborrades
     * @author RIS
     */
    public fun eliminarLlista(): Int {
        val filesAfectades: Int = usuariDao.deleteAll()
        return filesAfectades
    }
}