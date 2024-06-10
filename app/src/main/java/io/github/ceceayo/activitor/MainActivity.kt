package io.github.ceceayo.activitor

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.telephony.CarrierConfigManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import io.github.ceceayo.activitor.ui.theme.ActiviTorTheme


class MainActivity : ComponentActivity() {
    private val pushNotificationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        println(granted)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            AppDatabase.getInstance(this)

            ActiviTorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { pv ->
                    Column(Modifier.fillMaxSize().padding(pv)) {

                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                pushNotificationPermissionLauncher
                    .launch(android.Manifest.permission.POST_NOTIFICATIONS)
            startServerService(LocalContext.current, appConfig("jesse", "localhost"))
        }
    }
}