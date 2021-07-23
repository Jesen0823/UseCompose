package com.example.ui_abstraction

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui_abstraction.ui.theme.LayoutsCodeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                }
            }
        }
    }
}

@Composable
fun PhotographerCard(){
    Column() {
        Text(text = "Alfred Sisley", fontWeight = FontWeight.Bold)
        CompositionLocalProvider( LocalContentAlpha provides ContentAlpha.medium) {
            Text(text = "3 minutes ago", style = MaterialTheme.typography.body2)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    LayoutsCodeTheme {
        PhotographerCard()
    }
}



