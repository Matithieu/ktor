package com.mat.config

import com.mat.models.Tickets
import com.mat.models.Users
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import javax.sql.DataSource

fun configureDatabases() {
    val dataSource = createHikariDataSource()
    Database.connect(dataSource)
    setupTables()
}

// ToFix - This is not the best way to handle this
fun createHikariDataSource(): DataSource {
    val config = HikariConfig().apply {
        jdbcUrl = System.getenv("POSTGRES_URL") ?: "jdbc:postgresql://localhost:5432/tryhard"
        driverClassName = "org.postgresql.Driver"
        username = System.getenv("POSTGRES_USER") ?: "username"
        password = System.getenv("POSTGRES_PASSWORD") ?: "password"
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
    }

    return HikariDataSource(config)
}

fun setupTables() {
    transaction {
        SchemaUtils.create(Tickets)
        SchemaUtils.create(Users)
    }
}