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
import androidx.room.Room
import io.ktor.server.netty.NettyApplicationEngine

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

        server = startServer(user!!, host!!)
        server.start()
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
