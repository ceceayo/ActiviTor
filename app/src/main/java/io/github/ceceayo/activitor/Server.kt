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
import jakarta.json.Json
import jakarta.json.JsonObject
import jakarta.json.JsonObjectBuilder
import org.json.JSONObject
import java.io.InputStreamReader
import java.io.Reader

fun startServer(user: String, host: String, db: AppDatabase): NettyApplicationEngine {
    return embeddedServer(Netty, port=8080) {
        install(CallLogging)
        routing {
            get ("/") {
                call.respondText(db.userDao().getAll().toString())
                //call.respondText("hello world")
            }
            get ("/test/insert") {
                println("/test/insert")
                db.userDao().createPost("{\"type\":\"Create\"}")
                call.respondText("ok (rammstein reference)")
            }
            get ("/.well-known/webfinger") {
                println(call.request)
                if (call.request.queryParameters["resource"] == "acct:${user}@${host}") {
                    call.respondText(createWebFinger(user, host, call.request.queryParameters["resource"]!!, ""), ContentType.Application.Json)
                }
                call.respond(HttpStatusCode.NotFound, "")
            }

            get("/user/0/outbox") {
                val r = db.userDao().getAll().filter {
                    JSONObject(it.content).has("type") && JSONObject(it.content).get("type") == "Create"
                }
                val items = Json.createArrayBuilder()
                r.forEach {
                    val x = Json.createObjectBuilder()
                    Json.create
                }
                call.respondText(
                Json.createObjectBuilder()
                    .add("@context","https://www.w3.org/ns/activitystreams")
                    .add("id", "$host/user/0/outbox")
                    .add("type", "OrderedCollection")
                    .add("totalItems", r.size)
                    .add("orderedItems", Json.createArrayBuilder())
                    .build()
                    .toString(),
                    ContentType.parse("application/activity+json")
                )
            }
        }
    }

}