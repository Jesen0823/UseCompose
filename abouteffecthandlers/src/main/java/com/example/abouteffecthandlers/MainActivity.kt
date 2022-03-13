package com.example.abouteffecthandlers

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*

class MainActivity : ComponentActivity() {

    // private var i = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var text by remember {
                mutableStateOf("")
            }

            UseComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    // 1. 不使用LaunchedEffect
                    /*Button(onClick = { text += "#" }) {
                        i++
                        Text(text = text)
                    }*/

                    // 2. 使用LaunchedEffect
                    LaunchedEffect(key1 = text) {
                        delay(1000L)
                        println("The text is : $text")
                    }
                }
            }
        }
    }
}
