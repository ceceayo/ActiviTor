package io.github.ceceayo.activitor.routes

import jakarta.json.Json
import jakarta.json.JsonObject

fun createWebFinger(username: String, hostname: String, subject: String, profile: String): String {
    lateinit var objToSend: JsonObject
    try {
        objToSend = Json.createObjectBuilder()
            .add("subject", subject)
            .add("aliases", Json.createArrayBuilder())
            .add(
                "links", Json.createArrayBuilder()
                    .add(
                        Json.createObjectBuilder()
                            .add("rel", "self")
                            .add("type", "application/activity+json")
                            .add("href", "${hostname}${profile}")
                    )
            )
            .build()

    } catch (cause: Throwable) {
        println(cause.toString())

    }
    return objToSend.toString()

}