package com.mat.routes

import com.mat.models.User
import com.mat.repositories.UserRepository
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.userRoutine(userRepository: UserRepository) {
    routing {
        route("/users") {
            get {
                val users = userRepository.getAllUsers()
                call.respond(users)
            }

            get("/{id}") {
                val id: Int? = call.parameters["id"]?.toIntOrNull()

                if (id == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val user = userRepository.getUserById(id)
                if (user == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                call.respond(user)
            }

            post {
                try {
                    val user = call.receive<User>()
                    userRepository.addUser(user)
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

                if (userRepository.deleteUser(id)) {
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }
    }
}