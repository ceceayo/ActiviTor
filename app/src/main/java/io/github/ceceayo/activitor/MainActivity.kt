package io.github.ceceayo.activitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.github.ceceayo.activitor.ui.theme.ActiviTorTheme
import io.ktor.application.Application
import io.ktor.server.engine.*
import io.ktor.server.netty.*


class MainActivity : ComponentActivity() {
    private lateinit var server: NettyApplicationEngine
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
        }
        startServer()
    }
    private fun startServer() {
        server = embeddedServer(Netty, port = 8080, module = Application::module)
        server.start()
    }

    override fun onDestroy() {
        server.stop(0, 0)
        super.onDestroy()
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