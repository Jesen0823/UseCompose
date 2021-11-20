package com.jesen.driverexampaging.ui.composeview

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jesen.driverexampaging.R
import com.jesen.driverexampaging.ui.theme.RedPink


@Composable
fun ErrorPage(onclick: () -> Unit = {}) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(219.dp, 119.dp),
            painter = painterResource(id = R.drawable.ic_default_empty),
            contentDescription = "网络问题",
            contentScale = ContentScale.Crop
        )
        Button(
            modifier = Modifier.padding(8.dp),
            onClick = onclick,
        ) {
            Text(text = "网络不佳，请点击重试")
        }
    }
}

@Composable
fun LoadingPage() {
    val animator by rememberInfiniteTransition().animateFloat(
        initialValue = 30f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            tween(500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )
    Canvas(modifier = Modifier.fillMaxSize()) {
        translate(80f, 80f) {
            drawArc(
                RedPink,
                0f,
                animator,
                false,
                size = Size(80 * 2f, 80 * 2f),
                style = Stroke(4f),
                alpha = 0.6f
            )
        }
    }
}