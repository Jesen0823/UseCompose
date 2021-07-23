package com.example.ui_abstraction.simple_cutom_layout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NavigateNext
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ui_abstraction.ui.theme.LayoutsCodeTheme

import androidx.compose.ui.tooling.preview.Preview
import com.example.ui_abstraction.LayoutsCodelab
import com.example.ui_abstraction.MyOwnColumnLayout

class CustomLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsCodeTheme {
                CustomLayoutLab()
            }
        }
    }
}

@Composable
fun CustomLayoutLab() {
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
        BodyContentLay(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContentLay(modifier: Modifier = Modifier) {
        MyOwnColumnLayout(modifier.padding(8.dp)) {
            Text("MyOwnColumn")
            Text("places items")
            Text("vertically.")
            Text("We've done it by hand!")
        }
}

@Preview
@Composable
fun CustomLayoutLabPreview() {
    LayoutsCodeTheme {
        LayoutsCodelab()
    }
}