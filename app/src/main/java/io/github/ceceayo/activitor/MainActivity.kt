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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.ceceayo.activitor.ui.theme.ActiviTorTheme
import io.ktor.application.Application
import io.ktor.server.engine.*
import io.ktor.server.netty.*


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
            ActiviTorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { _ ->
                    Button(onClick = {

                    }) {
                        Text("Hi")
                    }
                }
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                pushNotificationPermissionLauncher
                    .launch(android.Manifest.permission.POST_NOTIFICATIONS)
            startServerService(LocalContext.current)
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ActiviTorTheme {
        Greeting("Android")
    }
}