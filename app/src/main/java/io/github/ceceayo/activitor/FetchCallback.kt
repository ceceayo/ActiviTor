package io.github.ceceayo.activitor

import info.guardianproject.netcipher.client.StrongBuilder
import java.lang.Exception
import java.net.HttpURLConnection

class FetchCallback : StrongBuilder.Callback<HttpURLConnection> {
    lateinit var con: HttpURLConnection
    var done = false
    var success = false
    /**
     * Called when the NetCipher-enhanced connection is ready
     * for use.
     *
     * @param connection the connection
     */
    override fun onConnected(connection: HttpURLConnection?) {
        println("onconnected")
        if (connection != null) {
            con = connection
            done = true
            success = true
        } else {
            done = true
            success = false
        }

    }

    /**
     * Called if we tried to connect through to Orbot but failed
     * for some reason
     *
     * @param e the reason
     */
    override fun onConnectionException(e: Exception?) {
        done = true
        success = false
        println(e.toString())
    }

    /**
     * Called if our attempt to get a status from Orbot failed
     * after a defined period of time. See statusTimeout() on
     * OrbotInitializer.
     */
    override fun onTimeout() {
        done = true
        success = false
        println("time out")
    }

    /**
     * Called if you requested validation that we are connecting
     * through Tor, and while we were able to connect to Orbot, that
     * validation failed.
     */
    override fun onInvalid() {
        done = true
        success = false
        println("invalid")
    }
}