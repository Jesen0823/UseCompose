package com.jesen.driverexampaging.ui.composeview

import androidx.compose.animation.*
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement.Absolute.SpaceBetween
import androidx.compose.foundation.layout.Arrangement.SpaceBetween
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SwapVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jesen.driverexampaging.model.Question

import com.jesen.driverexampaging.ui.theme.*
import org.intellij.lang.annotations.JdkConstants

/**
 * 题目卡片
 * */

@Composable
fun QItemView(que: Question?, onClick: () -> Unit, index: Int) {

    // 如果选项内容不为空就是选择题，为空就是判断题，需要展示不同UI
    val isSelectorType = (!que?.option1.isNullOrBlank() && !que?.option2.isNullOrBlank())

    // 是否展开答案
    var expandedState by remember { mutableStateOf<Boolean>(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(15.dp),
        elevation = 12.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(4.dp))
                .background(MaterialTheme.colors.surface)
                .padding(5.dp)
        ) {

            Column(modifier = Modifier.wrapContentSize()) {
                val titleDes = if (isSelectorType) "【选择题】" else "【判断题】"
                Text(
                    text = "${index.plus(1)} $titleDes：${que?.question}",
                    fontWeight = FontWeight.Bold,
                    style = TextStyle(fontSize = 22.sp),
                    color = Color.Black
                )
                CompositionLocalProvider(
                    LocalContentAlpha provides ContentAlpha.medium
                ) {

                    Text(
                        text = "${if (isSelectorType) que?.option1 else "√"}",
                        style = typography.subtitle1,
                        maxLines = 2,
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 8.dp)
                    )
                    Text(
                        text = "${if (isSelectorType) que?.option2 else "×"}",
                        style = typography.subtitle1,
                        maxLines = 2,
                        modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 8.dp)
                    )
                    if (isSelectorType) {
                        Text(
                            text = "${que?.option3}",
                            style = typography.subtitle1,
                            maxLines = 2,
                            modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 8.dp)
                        )
                        Text(
                            text = "${que?.option4}",
                            style = typography.subtitle1,
                            maxLines = 2,
                            modifier = Modifier.padding(start = 16.dp, top = 12.dp, end = 5.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = if (expandedState) "正确答案：${que?.answer}" else "请选择",
                        style = TextStyle(
                            fontFamily = FontFamily.Monospace,
                            textAlign = TextAlign.Start,
                            fontSize = 16.sp,
                            color = Purple200,
                            background = gray200
                        ),
                        modifier = Modifier.padding(start = 16.dp, bottom = 5.dp)
                    )
                    IconButton(
                        onClick = { expandedState = !expandedState },
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(end = 10.dp)
                            .alpha(0.7f)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.SwapVert,
                            tint = Color.Green,
                            contentDescription = "kook answer",
                            modifier = Modifier
                                .size(48.dp)
                                .padding(10.dp)
                        )
                    }

                }

                ChildResult(que?.explain, expandedState)
            }
        }
    }
}

/**
 * 题目答案解析
 * */
@Composable
fun ChildResult(explain: String?, visible: Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically(
            // Enters by sliding down from offset -fullHeight to 0.
            initialOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 150, easing = LinearOutSlowInEasing)
        ) + fadeIn(),
        exit = slideOutVertically(
            // Exits by sliding up from offset 0 to -fullHeight.
            targetOffsetY = { fullHeight -> -fullHeight },
            animationSpec = tween(durationMillis = 250, easing = FastOutLinearInEasing),
        ) + fadeOut(),
    ) {
        Surface(
            modifier = Modifier
                .wrapContentSize()
                .padding(16.dp),
            color = gray200,
            elevation = 4.dp,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = explain ?: "",
                style = TextStyle(
                    color = Color.Green,
                    fontSize = 18.sp,
                    fontFamily = FontFamily.Serif,
                    fontWeight = FontWeight(8)
                ),
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
            )
        }
    }
}