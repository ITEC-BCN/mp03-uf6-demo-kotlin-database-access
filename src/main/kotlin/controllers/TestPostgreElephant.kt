package org.example.controllers

import org.example.*
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement

fun main() {
    val url = "jdbc:postgresql://dumbo.db.elephantsql.com/"
    // Elephant uses the username to point to a specific database
    val username = "ajpkfrpe"
    val password = "VeRs0MV7dDAR1G8OEQikuUq7ZbxpuWgj"

    var exit: Boolean = false

    // Connect to BD
    var dbConnection: Connection? = connectToDB(url, username, password)
    var query: String
    var resultData: ResultSet?

    do {
        showDBTables(dbConnection)
        // Get the SQL query
        query = readSentence(CYAN_BACKGROUND + "Write a SQL query that you want to execute..." + RESET, "This may not be a valid SQL sentence...")
        // Execute the SQL query
        resultData = getData(dbConnection, query)
        // Print the results through terminal
        printData(resultData)

        exit = readBoolean(CYAN_BACKGROUND +"Write true to exit the program, false to execute another query"+ RESET, "Please write true or false")
    }while(!exit)
}

fun connectToDB(url: String, username: String, password: String): Connection? {
    // Check if the PostgreSQL driver is installed inside the project. As is a Gradle project, it should be inside the build.gradle.kts file
    try {
        Class.forName("org.postgresql.Driver")
    } catch (e: Exception) {
        println(e)
    }

    var db: Connection? = null

    try {
        db = DriverManager.getConnection(url, username, password)
        println(GREEN_BOLD + "Connection successful" + RESET)
    } catch (e: Exception) {
        println(e)
    }

    return db ?: throw IllegalStateException("Failed to establish database connection")
}

fun getData(dbConnection: Connection?, query: String): ResultSet? {
    var listStatement: Statement? = null
    var resultData: ResultSet? = null

    try {
        listStatement = dbConnection!!.createStatement()
        resultData = listStatement.executeQuery(query)

    } catch (e: Exception) {
        println(e)
    }

    return resultData
}

fun printData(resultData: ResultSet?) {
    try {
        if (resultData != null) {
            while (resultData.next()) {
                val id = resultData.getInt("index")
                val marca = resultData.getString("brand")
                val model = resultData.getString("model")
                val preu = resultData.getInt("price")
                println("$id - $marca - $model - $preu")
            }
        }
    } catch (e: Exception) {
        println("While trying to print the results... " + e)
    }finally {
        // Always close the ResultSet
        if (resultData != null) {
            resultData.close()
        }
    }
}

fun showDBTables(db: Connection?){
    val st: Statement? = db?.createStatement()
    val rs: ResultSet? = st?.executeQuery("SELECT * FROM INFORMATION_SCHEMA.tables WHERE table_type='BASE TABLE' AND table_name not like 'pg_%' AND table_name not like 'sql_%'")
    var tableCounter: Int = 1

    if (rs != null) {
        println(GREEN_BACKGROUND + "Available tables to be consulted in the given database:" + RESET + "\n")
        while (rs.next()) {
            val tableName: String = rs.getString(3)

            if (tableCounter % 2 == 0) {
                print(CYAN + "\t" + tableCounter + " -> ")
                println(tableName)
                printColumnNames(db, tableName)
            }else{
                print(BLUE + "\t" + tableCounter + " -> ")
                println(tableName)
                printColumnNames(db, tableName)
            }

            println(RESET)
            tableCounter++
        }
        println()
    }else{
        throw Exception("No tables available inside the given database!")
    }
    if (rs != null) {
        rs.close()
    }
}

fun printColumnNames(db: Connection, tableName: String) {
    val st: Statement? = db?.createStatement()
    val columns: ResultSet? = st?.executeQuery("SELECT column_name FROM INFORMATION_SCHEMA.columns WHERE table_name = '" + tableName + "' AND table_name not like 'pg_%' AND table_name not like 'sql_%'")

    if (columns != null) {
        while (columns.next()) {
            val columnName: String = columns.getString(1)
            println("\t\t\t" + columnName)
        }
    }
}
