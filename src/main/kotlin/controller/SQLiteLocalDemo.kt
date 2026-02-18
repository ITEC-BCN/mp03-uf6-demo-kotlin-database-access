package org.example.controllers

import model.NivellUsuari
import model.Usuari
import org.example.readInt
import org.example.readSentence
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

//fem servir la ruta i nom de la base de dades com a global, ja que aquesta no canvia
val DATABASEFILE = "src/databases/BDExempleI.db"

public fun menuSQLite() {
    do {
        println("############")
        println("### MENÚ ###")
        println("############")
        println(
            """
            1. Mostrar dades
            2. Afegir usuari
            3. Modificar usuari
            4. Eliminar usuari
            0. Sortir
        """.trimIndent()
        )
        val opcio = readInt("Tria una opció", "Ha de ser numèric", "Les opcions son entre 0 i 4", 0, 4)
        when (opcio) {
            1 -> llistarUsuaris()
            2 -> nouUsuari()
            3 -> modificarUsuari(seleccionarUsuari())
            4 -> eliminarUsuari(seleccionarUsuari())
            0 -> println("A reveure!")
        }
    } while(opcio!=0)
}

private fun llistarUsuaris(){
    println("##########################")
    println("### USUARIS ACTUALS A LA BD ###")
    println("##########################")
    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "SELECT * FROM usuari ORDER BY usu_nom"
        val resultat: ResultSet = listStatement.executeQuery(query)

        while (resultat.next()) {
            val id = resultat.getInt("usu_id")
            val nom = resultat.getString("usu_nom")
            val nivell = resultat.getString("usu_nivell")
            println("$id - $nom - $nivell")
        }
    }//tancament de la connexió
}

private fun seleccionarUsuari(): Int {
    var actualUserIds: MutableList<Int> = getUserIds()
    llistarUsuaris()
    var idUsuari: Int = readInt("Escriu l'id de l'usuari a modificar",
        "Valor no numèric",
        "Valor fora de rang",
        actualUserIds.min(),actualUserIds.max())
    return idUsuari
}

private fun nouUsuari(id: Int = -1){
    println("#####################")
    println("### NOU USUARI ###")
    println("#####################")
    var idUsuari: Int = id

    if (idUsuari == -1)
        idUsuari = readInt("Introdueix l'ID de l'usuari", "Ha de ser un numèric")

    val nom: String = readSentence("Introdueix el NOM de l'usuari", "Ha de ser un text")
    var nivellText: String

    var nivellCorrecte: Boolean = false
    do {
        nivellText = readSentence("Introdueix el NIVELL D'ACCÉS de l'usuari dins dels següents ${NivellUsuari.entries}", "Ha de ser un dels valors ${NivellUsuari.entries}")

        for (n in NivellUsuari.entries) {
            if (n.name == nivellText)
                nivellCorrecte = true
        }
    }while (!nivellCorrecte)

    val nivell: NivellUsuari = convertNivellUsuari(nivellText)

    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "INSERT INTO usuari (usu_id, usu_nom, usu_nivell) VALUES ($idUsuari, '$nom', '$nivell')"
        println(query)
        listStatement.executeUpdate(query)  //per fer comandes d'actulització (INSERT, UPDATE, DELETE) farem servir executeUpdate, no executeQuery
    }//tancament de la connexió
}

private fun convertNivellUsuari(n: String): NivellUsuari{
    val nivell: NivellUsuari =
        when(n){
            "DOCENT" -> NivellUsuari.DOCENT
            "COORDINADOR" -> NivellUsuari.COORDINADOR
            "DIRECTIU" -> NivellUsuari.DIRECTIU
            else -> NivellUsuari.DOCENT
        }

    return nivell
}

private fun modificarUsuari(idUsuari: Int){
    var usuariModificar: Usuari? = getUsuariById(idUsuari)
    println("L'usuari a modificar és:")
    println(usuariModificar)
    eliminarUsuari(idUsuari)
    nouUsuari(idUsuari)
}

private fun getUserIds(): MutableList<Int>{
    var actualUserIds: MutableList<Int> = mutableListOf()

    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "SELECT DISTINCT usu_id FROM usuari ORDER BY usu_id"
        val resultat: ResultSet = listStatement.executeQuery(query)

        while (resultat.next()) {
            val id = resultat.getInt("usu_id")
            actualUserIds.add(id)
        }
    }//tancament de la connexió
    return actualUserIds
}

fun getUsuariById(idUsuari: Int): Usuari? {
    var user: Usuari? = null

    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "SELECT * FROM usuari WHERE usu_id = $idUsuari LIMIT 1"
        val resultat: ResultSet = listStatement.executeQuery(query)

        if (resultat.next()) {
            val id = resultat.getInt("usu_id")
            val nom = resultat.getString("usu_nom")
            val nivell: NivellUsuari = convertNivellUsuari(resultat.getString("usu_nivell"))

            user = Usuari(id, nom, nivell)
        }
    }//tancament de la connexió

    return user
}

private fun eliminarUsuari(id: Int): Int{
    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "DELETE FROM usuari WHERE usu_id = $id"
        //println(query)
        listStatement.executeUpdate(query)  //per fer comandes d'actulització (INSERT, UPDATE, DELETE) farem servir executeUpdate, no executeQuery

        return listStatement.updateCount
    }//tancament de la connexió
}