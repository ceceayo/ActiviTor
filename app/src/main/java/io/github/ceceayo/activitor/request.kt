package io.github.ceceayo.activitor

import info.guardianproject.netcipher.client.StrongConnectionBuilder

fun fetchActor(con: ServerService, actor: String) {
    val callback = FetchCallback()
    val connection = StrongConnectionBuilder
        .forMaxSecurity(con)
        .withTorValidation()
        .withSocksProxy()
    println("1")
    connection.connectTo("https://myip.is/")
    println("2")
    connection.build(callback)
    println("3")
    println(con.toString())
    println(callback.toString())
    println(connection.toString())
}