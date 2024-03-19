package org.example

import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

//fem servir la ruta i nom de la base de dades com a global, ja que aquesta no canvia
val DATABASEFILE = "src/databases/BDExempleI.db"

fun main() {
    menu()

}

fun menu() {
    do {
        println("############")
        println("### MENÚ ###")
        println("############")
        println(
            """
            1. Mostrar dades
            2. Afegir usuari
            3. Eliminar usuari
            4. Modificar usuari
            0. Sortir
        """.trimIndent()
        )
        val opcio = readInt("Tria una opció", "Ha de ser numèric", "Les opcions son entre 0 i 4", 0, 4)
        when (opcio) {
            1 -> veureDades()
            2 -> afegirUsuari()
            3 -> {

            }

            4 -> {

            }

            0 -> println("A reveure!")
        }
    } while(opcio!=0)
}

fun veureDades(){
    println("##########################")
    println("### MOSTRANT LES DADES ###")
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

fun afegirUsuari(){
    println("#####################")
    println("### AFEGIR USUARI ###")
    println("#####################")
    val id = readInt("Introdueix l'ID de l'usuari", "Ha de ser un numèric")
    val nom = readSentence("Introdueix el NOM de l'usuari", "Ha de ser un text")
    val nivell = readSentence("Introdueix el NIVELL D'ACCÉS de l'usuari", "Ha de ser un text")


    DriverManager.getConnection("jdbc:sqlite:$DATABASEFILE").use { connection ->
        val listStatement: Statement = connection.createStatement()
        val query = "INSERT INTO usuari (usu_id, usu_nom, usu_nivell) VALUES ($id, '$nom', '$nivell')"
        println(query)
        listStatement.executeUpdate(query)  //per fer comandes d'actulització (INSERT, UPDATE, DELETE) farem servir executeUpdate, no executeQuery
    }//tancament de la connexió

}