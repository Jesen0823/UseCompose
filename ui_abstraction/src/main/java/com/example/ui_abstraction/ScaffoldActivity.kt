package com.example.ui_abstraction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.ui_abstraction.ui.theme.LayoutsCodeTheme

class ScaffoldActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_scaffold)
        setContent {
            LayoutsCodeTheme {
                LayoutsCodelab()
            }
        }
    }
}

@Composable
fun LayoutsCodelab() {
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
                        Icon(Icons.Filled.Favorite, contentDescription = null)
                    }
                }
            )
        }
    ) { innerPadding ->
        BodyContent(
            Modifier
                .padding(innerPadding)
                .padding(8.dp)
        )
    }
}

@Composable
fun BodyContent(modifier: Modifier = Modifier) {
    Column(modifier = modifier.padding(8.dp)) {
        Text(text = "Hi there")
        Text(text = "Thanks for going through the Layouts codelab")
        SimpleList()
        ImageList()
    }
}

// 普通文字列表
@Composable
fun SimpleList() {
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState) {
        items(20) {
            Text("Item #$it")
        }
    }
}

// 图文列表的Item
@Composable
fun ImageListItem(index: Int) {
    val imageUrl =
        "https://dribbble.com/assets/default_avatars/avatar-1-097423283dcf837a2838017f37c1ba97677af3a32e0eadfde1a182ff8f77fc9d.png"
    Row(verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = rememberImagePainter(
                data = "https://dribbble.com/assets/default_avatars/avatar-1-097423283dcf837a2838017f37c1ba97677af3a32e0eadfde1a182ff8f77fc9d.png"

            ),
            contentDescription = "Android Logo",
            modifier = Modifier.size(50.dp)
        )
        Spacer(Modifier.width(10.dp))
        Text("Item #$index", style = MaterialTheme.typography.subtitle1)
    }
}

// 图文列表
@Composable
fun ImageList() {
    val scrollState = rememberLazyListState()

    LazyColumn(state = scrollState) {
        items(100) {
            ImageListItem(index = it)
        }
    }
}

@Preview
@Composable
fun LayoutsCodelabPreview() {
    LayoutsCodeTheme {
        LayoutsCodelab()
    }
}






