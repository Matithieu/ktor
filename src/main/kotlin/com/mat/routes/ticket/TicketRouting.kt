package com.mat.routes.ticket

import com.mat.models.Ticket
import com.mat.repositories.TicketRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.ticketRoutine(ticketRepository: TicketRepository) {
    routing {
        route("/tickets") {
            get {
                val tickets = ticketRepository.getAllTickets()
                call.respond(tickets)
            }

            get("/{id}") {
                val id: Int? = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val ticket = ticketRepository.getTicketById(id)
                if (ticket == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(ticket)
            }

            post {
                try {
                    val ticket = call.receive<Ticket>()
                    ticketRepository.addTicket(ticket)
                    call.respond(HttpStatusCode.NoContent)
                } catch (ex: IllegalStateException) {
                    call.respond(HttpStatusCode.BadRequest)
                } catch (ex: JsonConvertException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            }

            delete("/{id}") {
                val id: Int? = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                if (ticketRepository.deleteTicket(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}
