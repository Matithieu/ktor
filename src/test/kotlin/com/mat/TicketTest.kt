package com.mat

import com.mat.config.configureDatabases
import com.mat.models.Priority
import com.mat.models.Ticket
import com.mat.repositories.TicketRepository
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class TicketTest {

    @Test
    fun ticketsCanBeCreatedAndFound() = testApplication {
        application {
            configureDatabases()

            val ticketRepository = TicketRepository()
            val testTicket1 = Ticket(1, "Title", "Description", Priority.Low)
            val testTicket2 = Ticket(2, "Title", "Description", Priority.Low)

            ticketRepository.addTicket(testTicket1)
            ticketRepository.addTicket(testTicket2)

            val ticketsJustCreated = ticketRepository.getAllTickets()
            assertTrue(ticketsJustCreated.isNotEmpty(), "There should be at least 2 tickets!")
        }
    }

    @Test
    fun ticketCanBeCreatedAndFound() = testApplication {
        application {
            configureDatabases()

            val ticketRepository = TicketRepository()
            val testTicket = Ticket(1, "Title", "Description", Priority.Low)

            val ticketResponse = ticketRepository.addTicket(testTicket)

            val ticketJustCreated = ticketRepository.getTicketById(ticketResponse.id)
            assertNotNull(ticketJustCreated, "Ticket should be created!")
        }
    }

    @Test
    fun ticketCanBeCreatedAndDeleted() = testApplication {
        application {
            configureDatabases()

            val ticketRepository = TicketRepository()
            val testTicket = Ticket(1, "Title", "Description", Priority.Low)

            val ticketResponse = ticketRepository.addTicket(testTicket)

            val ticketJustCreated = ticketRepository.deleteTicket(ticketResponse.id)
            assertTrue(ticketJustCreated, "Ticket should be deleted!")
        }
    }
}