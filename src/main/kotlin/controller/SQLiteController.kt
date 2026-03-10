package org.example.controllers

import dao.UsuariDao
import model.Usuari

public class SQLiteController {

    /**
     * Funció del controller que retorna una List no modificable amb totes les dades de tots els Usuaris de la BD
     * @author RIS
     */
    public fun getUsuaris(): List<Usuari> {
        val llistaUsuaris: List<Usuari> = UsuariDao.getAllUsers()
        return llistaUsuaris
    }

    /**
     * Funció de controller que retorna el llistat de ids d'Usuari existents dins de la BD
     * @return ids d'Usuari existents dins de la BD
     * @author RIS
     */
    public fun idsUsuari(): List<Int> {
        var idsUsuariActuals: List<Int> = UsuariDao.getUserIdList()

        return idsUsuariActuals
    }

    /**
     * Funció del controller que intenta buscar l'usuari corresponent a un id
     * @param idUsuari de tipus Int
     * @return Un Usuari o null
     * @author RIS
     */
    fun getUsuariById(idUsuari: Int): Usuari? {
        val user: Usuari? = UsuariDao.getUserById(idUsuari)
        return user
    }

    /**
     * Funció del controller que insereix l'usuari rebut per paràmetre dins de la BD a través d'UsuariDao
     * @author RIS
     */
    public fun inserirUsuari(usuari: Usuari): Int{
        val filesAfectades: Int = UsuariDao.insertUser(usuari)
        return filesAfectades
    }

    /**
     * Funció del controller que actualitza l'usuari rebut per paràmetre dins de la BD a través d'UsuariDao
     * @author RIS
     */
    public fun modificarUsuari(usuari: Usuari): Int {
        val filesAfectades: Int = UsuariDao.updateUser(usuari)
        return filesAfectades
    }

    /**
     * Funció de controller que envia un Usuari a eliminar
     * @param usuari a eliminar
     * @return rowcount de files afectades
     * @author RIS
     */
    public fun eliminarUsuari(usuari: Usuari): Int {
        val filesAfectades: Int = UsuariDao.deleteUser(usuari)
        return filesAfectades
    }

    /**
     * Funció del controller que elimina totes les files de la taula usuaris
     * @return retorna el nombre de files esborrades
     * @author RIS
     */
    public fun eliminarLlista(): Int {
        val filesAfectades: Int = UsuariDao.deleteAll()
        return filesAfectades
    }
}