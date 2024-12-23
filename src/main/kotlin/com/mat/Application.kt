package com.mat

import com.mat.config.configureDatabases
import com.mat.config.configureHTTP
import com.mat.routes.configureRouting
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureHTTP()
    configureDatabases()
    configureRouting()
}
