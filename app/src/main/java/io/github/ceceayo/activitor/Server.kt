package io.github.ceceayo.activitor



import io.github.ceceayo.activitor.routes.createWebFinger
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.callloging.CallLogging

fun startServer(user: String, host: String): NettyApplicationEngine {
    return embeddedServer(Netty, port=8080) {
        install(CallLogging)
        routing {
            get ("/") {

                call.respondText("hello world")
            }
            get ("/.well-known/webfinger") {
                println(call.request)
                if (call.request.queryParameters["resource"] == "acct:${user}@${host}") {
                    call.respondText(createWebFinger(user, host, call.request.queryParameters["resource"]!!, ""), ContentType.Application.Json)
                }
                call.respond(HttpStatusCode.NotFound, "")
            }
        }
    }

}