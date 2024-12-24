package com.mat

import com.mat.config.configureDatabases
import com.mat.models.User
import com.mat.repositories.UserRepository
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class UserTest {

    private val testUser1 = User(1, "User1", "email1@mail.com")
    private val testUser2 = User(2, "User2", "email2@mail.com")

    @Test
    fun usersCanBeCreatedAndFound() = testApplication {
        application {
            configureDatabases()

            val userRepository = UserRepository()

            userRepository.addUser(testUser1)
            userRepository.addUser(testUser2)

            val usersJustCreated = userRepository.getAllUsers()
            assertTrue(usersJustCreated.isNotEmpty(), "There should be at least 2 users!")
        }
    }

    @Test
    fun userCanBeCreatedAndFound() = testApplication {
        application {
            configureDatabases()

            val userRepository = UserRepository()

            val userResponse = userRepository.addUser(testUser1)

            val userJustCreated = userRepository.getUserById(userResponse.id)
            assertNotNull(userJustCreated, "User should be created!")
        }
    }

    @Test
    fun userCanBeCreatedAndDeleted() = testApplication {
        application {
            configureDatabases()

            val userRepository = UserRepository()

            val userResponse = userRepository.addUser(testUser1)

            val userJustCreated = userRepository.deleteUser(userResponse.id)
            assertTrue(userJustCreated, "User should be deleted!")
        }
    }
}