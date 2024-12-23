package com.mat.models

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

enum class Priority {
    Low, Medium, High
}

@Serializable
data class Ticket (
    val id: Int,
    val title: String,
    val description: String,
    val priority: Priority,
)

object Tickets : Table() {
    val id = integer("id").autoIncrement() // Primary key
    val title = varchar("title", 255)
    val description = text("description")
    val priority = enumerationByName("priority", 50, Priority::class)

    override val primaryKey = PrimaryKey(id)
}