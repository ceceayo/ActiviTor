package io.github.ceceayo.activitor

import info.guardianproject.netcipher.client.StrongConnectionBuilder

fun fetchActor(con: ServerService, actor: String) {
    val con = StrongConnectionBuilder
        .forMaxSecurity(con)
        .withTorValidation()
        .withSocksProxy()

}