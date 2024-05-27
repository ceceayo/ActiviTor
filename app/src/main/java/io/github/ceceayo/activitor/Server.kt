package io.github.ceceayo.activitor

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.routing.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.module() {
    install(ContentNegotiation) {
        gson()
    }

    routing {
        get("/api/data") {
            call.respond(mapOf("message" to "Hello, world!"))
        }
    }
}