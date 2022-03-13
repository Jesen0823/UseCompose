package com.example.abouteffecthandlers.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier

class InteractionActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            UseComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        item {
                            DragInteractionTest()
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            HorizontalDragTest()
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            ScrollableTest()
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            TransformableTest()
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                        item {
                            VerticalDragTest()
                            Spacer(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }
    }
}