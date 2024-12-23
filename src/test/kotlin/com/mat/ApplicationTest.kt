package com.mat

import com.mat.config.configureDatabases
import com.mat.config.configureHTTP
import com.mat.routes.configureRouting
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

class ApplicationTest {

    @Test
    fun testRoot() = testApplication {
        application {
            configureHTTP()
            configureDatabases()
            configureRouting()
        }

        client.get("/").apply {
            assertEquals(HttpStatusCode.OK, status)
        }
    }
}
