package com.example.usecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.usecompose.ui.theme.UseComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                MyScreenContent()
            }
        }
    }
}

@Composable
fun MyApp(content: @Composable () -> Unit) {
    UseComposeTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = Color.Yellow) {
            content()
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", modifier = Modifier.padding(24.dp))
}

@Composable
fun MyScreenContent(){
    Column() {
        Greeting(name = "Android")
        Divider(color = Color.Black)
        Greeting("there")
    }
}

@Preview("MyScreen preview")
@Composable
fun DefaultPreview() {
    MyApp {
        MyScreenContent()
    }
}