package com.mat.routes

import com.fasterxml.jackson.databind.SerializationFeature
import com.mat.repositories.TicketRepository
import com.mat.repositories.UserRepository
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val ticketRepository = TicketRepository()
    val userRepository = UserRepository()

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        get("/") {
            call.respond(HttpStatusCode.OK)
        }
    }

    routing {
        ticketRoutine(ticketRepository)
        userRoutine(userRepository)
    }
}
