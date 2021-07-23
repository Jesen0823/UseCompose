package com.example.ui_abstraction.complex_custom_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ui_abstraction.ui.theme.LayoutsCodeTheme

class CustomStagActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodeTheme() {
                CustomStagLayout()
            }
        }
    }
}

@Composable
fun CustomStagLayout() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "LayoutCodelab",
                        style = MaterialTheme.typography.h3
                    )
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(Icons.Filled.NavigateNext, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContents(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

val topics = listOf(
    "Arts & Crafts", "Beauty", "Books", "Business", "Comics", "Culinary",
    "Design", "Fashion", "Film", "History", "Maths", "Music", "People", "Philosophy",
    "Religion", "Social sciences", "Technology", "TV", "Writing"
)

@Composable
fun BodyContents(modifier: Modifier = Modifier) {
    StaggeredGrid(modifier = modifier){
        for (topic in topics) {
            Chip(modifier = Modifier.padding(8.dp), text = topic)
        }
    }
}

@Preview
@Composable
fun LayoutsStaggeredPreview() {
    LayoutsCodeTheme {
        BodyContents()
    }
}