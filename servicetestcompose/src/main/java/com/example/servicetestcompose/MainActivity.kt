package com.example.servicetestcompose

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.servicetestcompose.service.ForegroundService
import com.example.servicetestcompose.service.MyService
import com.example.servicetestcompose.ui.theme.UseComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UseComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val context = LocalContext.current
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .background(color = Color.Gray, shape = RoundedCornerShape(12.dp))
                    ) {
                        MyButtons(
                            startService = {
                                Intent(context, MyService::class.java).also {
                                    startService(it)
                                }
                            },
                            stopService = {
                                Intent(context, MyService::class.java).also {
                                    stopService(it)
                                }
                            },
                            text1 = "启动服务",
                            text2 = "停止服务"
                        )

                        MyButtons(
                            startService = {
                                Intent(context, ForegroundService::class.java).also {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                        startForegroundService(it)
                                    } else {
                                        startService(it)
                                    }
                                }
                            },
                            stopService = {
                                Intent(context, ForegroundService::class.java).also {
                                    stopService(it)
                                }
                            },
                            text1 = "启动前台服务",
                            text2 = "停止前台服务"
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun MyButtons(startService: () -> Unit, stopService: () -> Unit, text1: String, text2: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(onClick = { startService.invoke() }) {
            Text(text = text1)
        }
        Button(onClick = { stopService.invoke() }) {
            Text(text = text2)
        }
    }
}