package io.github.ceceayo.activitor

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import info.guardianproject.netcipher.proxy.OrbotHelper
import io.ktor.client.HttpClient
import io.ktor.client.engine.ProxyBuilder
import io.ktor.client.engine.http
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.request.request
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.server.netty.NettyApplicationEngine
import kotlinx.coroutines.runBlocking

public class ServerService : Service() {

    private val channelId = "my_channel_id"
    private val channelName = "My Channel"

    private lateinit var server: NettyApplicationEngine;

    override fun onCreate() {
        println("onCreate")
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        println("createNotificationChannel")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = channelName
            val descriptionText = "Channel description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        println("onStartCommand")

        if (intent?.hasExtra("user") != true) {
            stopSelf()
            return START_NOT_STICKY
        }
        if (!intent.hasExtra("host")) {
            stopSelf()
            return START_NOT_STICKY
        }
        val notification: Notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("Foreground Service")
            .setContentText("Running...")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()
        startForeground(NOTIFICATION_ID, notification)
        // Your service logic here
        val user = intent.getStringExtra("user")
        val host = intent.getStringExtra("host")

        val db = AppDatabase.getInstance(this)

        var oh = OrbotHelper.get(this)
        oh.init()
        try {

            Thread {
                println("aaa")
                lateinit var client: HttpClient
                client = HttpClient(OkHttp) {
                    engine {
                        proxy = ProxyBuilder.http("http://localhost:8118/")
                    }
                }

                runBlocking {
                    val response: HttpResponse = client.request("https://4.myip.is/")
                    println(response.bodyAsText())
                }
            }.start()

        } catch (e: Throwable) {
            println(e.toString())
        }

        if (!OrbotHelper.get(this).isInstalled) {
            println("no orbot WTF")
        }
        try {

            fetchActor(this, "a")
            server = startServer(user!!, host!!, db, this)
            server.start()
        } catch (e: Throwable) {
            println("error")
            println(e.toString())
        }
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        println("onBind")
        return null
    }

    companion object {
        const val NOTIFICATION_ID = 1
    }
}



public fun startServerService(context: Context, config: appConfig) {
    val serviceIntent = Intent(context, ServerService::class.java)
    serviceIntent.putExtras(bundleOf("user" to config.username, "host" to config.hostname))
    ContextCompat.startForegroundService(context, serviceIntent)
}
