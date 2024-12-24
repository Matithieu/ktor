package com.mat.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

@Serializable
data class User (
    val id: Int,
    val name: String,
    val email: String,
)

object Users : Table() {
    val id = integer("id").autoIncrement() // Primary key
    val name = varchar("name", 255)
    val email = varchar("email", 255)

    override val primaryKey = PrimaryKey(id)
}