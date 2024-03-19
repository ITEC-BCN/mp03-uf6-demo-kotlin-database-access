package org.example

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.Statement


fun main() {
    var dbConnection: Connection = init("jdbc:postgresql://peanut.db.elephantsql.com/", "kzyrpyiy", "oLhZYJxHXu3CBs_47ycBpB5U35sGS450")
    showEmployees(dbConnection)
}

fun showEmployees(dbConnection: Connection) {
    val query: Statement = dbConnection.createStatement()
    var resultData: ResultSet = query.executeQuery("SELECT * FROM employees")

    while(resultData.next()){
        print(resultData.getString(2))
        println(" " + resultData.getString(3))
    }
}

fun init(url: String, username: String, password: String): Connection {
    try {
        Class.forName("org.postgresql.Driver")
    } catch (e: Exception) {
        println(e)
    }

    val db : Connection

    db = DriverManager.getConnection(url, username, password)

    /*
    try {
        db = DriverManager.getConnection(url, username, password)
        val st: Statement = db.createStatement()
        val rs: ResultSet = st.executeQuery("SELECT * FROM INFORMATION_SCHEMA.tables WHERE table_type='BASE TABLE'")

        while (rs.next()) {
            print("Table Name: ")
            println(rs.getString(3))
        }
        rs.close()
        st.close()
    } catch (e: Exception) {
        println(e)
    }
    */

    return db
}
