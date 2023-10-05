package com.example.dao

import com.example.models.*
import kotlinx.coroutines.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.*
import org.jetbrains.exposed.sql.transactions.experimental.*

object DatabaseFactory {
    fun init() {
//        val driverClassName = "org.h2.Driver"
//        val jdbcURL = "jdbc:h2:~/incomedb"
        val driverClassName = "com.mysql.cj.jdbc.Driver"
        val jdbcURL = "jdbc:mysql://localhost:3306/newincomedb"
        val database = Database.connect(jdbcURL,driverClassName,"root","yooku@12")
        transaction(database) {
            SchemaUtils.createMissingTablesAndColumns(UserResponse)
        }
    }
}

suspend fun <T> dbQuery(block: () ->T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }
