package com.mat.interfaces

import com.mat.models.Ticket

interface TicketInterface {
    fun getAllTickets(): List<Ticket>
    fun getTicketById(id: Int): Ticket?
    fun addTicket(ticket: Ticket): Ticket
    fun updateTicket(ticket: Ticket): Boolean
    fun deleteTicket(id: Int): Boolean
}