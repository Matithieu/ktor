package com.mat.repositories

import com.mat.interfaces.TicketInterface
import com.mat.models.Ticket
import com.mat.models.Tickets
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class TicketRepository : TicketInterface {
    override fun getAllTickets(): List<Ticket> {
        return transaction {
            Tickets.selectAll().toList().map {
                Ticket(
                    id = it[Tickets.id],
                    title = it[Tickets.title],
                    description = it[Tickets.description],
                    priority = it[Tickets.priority]
                )
            }
        }
    }

    override fun getTicketById(id: Int): Ticket? {
        return transaction {
            Tickets
                .select(Tickets.id, Tickets.title, Tickets.description, Tickets.priority)
                .where(Tickets.id eq id)
                .map {
                    Ticket(
                        id = it[Tickets.id],
                        title = it[Tickets.title],
                        description = it[Tickets.description],
                        priority = it[Tickets.priority]
                    )
                }.singleOrNull()
        }
    }

    override fun addTicket(ticket: Ticket): Ticket {
        return transaction {
            val id = Tickets.insert {
                it[title] = ticket.title
                it[description] = ticket.description
                it[priority] = ticket.priority
            }

            Ticket(
                id = id[Tickets.id],
                title = ticket.title,
                description = ticket.description,
                priority = ticket.priority
            )
        }
    }


    override fun updateTicket(ticket: Ticket): Boolean {
        return transaction {
            Tickets.update({ Tickets.id eq ticket.id }) {
                it[title] = ticket.title
                it[description] = ticket.description
                it[priority] = ticket.priority
            } > 0
        }
    }

    override fun deleteTicket(id: Int): Boolean {
        return transaction {
            Tickets.deleteWhere { Tickets.id eq id } > 0
        }
    }
}