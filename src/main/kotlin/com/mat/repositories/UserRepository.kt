package com.mat.repositories

import com.mat.interfaces.UserInterface
import com.mat.models.User
import com.mat.models.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class UserRepository : UserInterface {
    override fun getAllUsers(): List<User> {
        return transaction {
            Users.selectAll().toList().map {
                User(
                    id = it[Users.id],
                    name = it[Users.name],
                    email = it[Users.email],
                )
            }
        }
    }

    override fun getUserById(id: Int): User? {
        return transaction {
            Users
                .select(Users.id, Users.name, Users.email)
                .where(Users.id eq id)
                .map {
                    User(
                        id = it[Users.id],
                        name = it[Users.name],
                        email = it[Users.email],
                    )
                }.singleOrNull()
        }
    }

    override fun addUser(ticket: User): User {
        return transaction {
            val id = Users.insert {
                it[name] = ticket.name
                it[email] = ticket.email
            }

            User(
                id = id[Users.id],
                name = ticket.name,
                email = ticket.email,
            )
        }
    }


    override fun updateUser(ticket: User): Boolean {
        return transaction {
            Users.update({ Users.id eq ticket.id }) {
                it[name] = ticket.name
                it[email] = ticket.email
            } > 0
        }
    }

    override fun deleteUser(id: Int): Boolean {
        return transaction {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }
}