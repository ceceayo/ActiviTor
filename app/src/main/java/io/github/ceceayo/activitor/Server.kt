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
import org.json.JSONObject
import java.io.ByteArrayInputStream

fun startServer(user: String, host: String, db: AppDatabase, serverService: ServerService): NettyApplicationEngine {
    return embeddedServer(Netty, port=8080) {
        install(CallLogging)
        routing {
            get ("/") {
                call.respondText(db.userDao().getAll().toString())
                //call.respondText("hello world")
            }
            get ("/test/insert") {
                println("/test/insert")
                db.userDao().createPost("{\"type\": \"Create\"}")
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
                println("outbox")
                val r = db.userDao().getAll().filter {
                    println("filter ${it.id}")
                    val r1 = JSONObject(it.content)
                    println(r1.toString())
                    r1.has("type") && r1.get("type") == "Create"
                }
                val items = Json.createArrayBuilder()
                r.forEach { it1 ->
                    println("foreach ${it1.id} / ${it1.content}")
                    val x = Json.createObjectBuilder()
                    println("1")
                    val y = Json.createReader(ByteArrayInputStream(it1.content.toByteArray(Charsets.UTF_8)))
                    println("2")
                    val readY = y.readObject()
                    if (readY.getString("type").toString() == "Create") {
                        println("4 / ${readY.toString()}")
                        readY.forEach { it2 ->
                            println("4 ${it2.key}=${it2.value}")
                            x.add(it2.key, it2.value)
                        }

                        x.add("id", "/user/0/posts/${it1.id}")
                        x.add("actor", "$host/user/0")
                        x.add("published", it1.created_at)
                        x.add("to", Json.createArrayBuilder()
                            .add("https://www.w3.org/ns/activitystreams#Public"))
                        println("5 ${x.toString()}")
                        items.add(x)
                        println("6 ${items.toString()}")
                    } else println("3/2")
                    println("7")
                }
                println(items.toString())
                call.respondText(
                Json.createObjectBuilder()
                    .add("@context","https://www.w3.org/ns/activitystreams")
                    .add("id", "$host/user/0/outbox")
                    .add("type", "OrderedCollection")
                    .add("totalItems", r.size)
                    .add("orderedItems", items)
                    .build()
                    .toString(),
                    ContentType.parse("application/activity+json")
                )
            }
            post ("/user/0/inbox") {
                println("Willkommen zum inbox!!!!!1!!!11!!!!!!")
                val params = call.request.queryParameters
                if (!params.contains("actor")) {call.respondText("WTF"); return@post}
                call.respondText ("OK (rammstein reference)")
            }
        }
    }

}