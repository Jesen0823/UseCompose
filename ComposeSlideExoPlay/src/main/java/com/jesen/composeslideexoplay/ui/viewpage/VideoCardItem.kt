package com.jesen.composeslideexoplay.ui.viewpage

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.jesen.composeslideexoplay.model.VideoItem

@Composable
fun VideoCardItem(videoItem: VideoItem, onClick: () -> Unit, index: Int) {
    Surface(color = MaterialTheme.colors.surface) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = videoItem.videoInfo?.description ?: "", style = MaterialTheme.typography.h6)
            Text(text = videoItem.videoInfo?.title ?: "", style = MaterialTheme.typography.body1)
        }
    }
}