package com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note.components

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector4D
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Save
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jesen.cleanarchitecture.feature_note.domain.model.NoteModel
import com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note.AddEditNoteEvent
import com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note.EditNoteViewModel
import com.jesen.cleanarchitecture.feature_note.presentation.add_edit_note.NoteTextFieldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun EditNoteDetailScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: EditNoteViewModel = hiltViewModel()
) {

    val titleState = viewModel.noteTitleState
    val contentState = viewModel.noteContentState
    val scaffoldState = rememberScaffoldState()

    val noteBackgroundAnimatable = remember {
        Animatable(
            // 背景色优先使用传进来的，如果没有，用viewModel中随机颜色
            Color(if (noteColor != -1) noteColor else viewModel.noteColorState.value)
        )
    }
    val scope = rememberCoroutineScope()

    // 搜集页面操作
    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is EditNoteViewModel.UIEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }
                is EditNoteViewModel.UIEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(AddEditNoteEvent.SaveNote)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Outlined.Save, contentDescription = "save note")
            }
        },
        scaffoldState = scaffoldState
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(noteBackgroundAnimatable.value)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val colorState = viewModel.noteColorState

                NoteModel.noteColors.forEach { color ->
                    val colorInt = color.toArgb()
                    val isSelectColor = colorState.value == colorInt
                    Box(
                        modifier = Modifier
                            .size(if (isSelectColor) 50.dp else 46.dp)
                            .shadow(15.dp, CircleShape)
                            .clip(CircleShape)
                            .background(color = color)
                            .advancedShadow(
                                color = if (isSelectColor) Color.Black else Color.Transparent,
                                offsetX = if (isSelectColor) 2.dp else 0.dp,
                                offsetY = if (isSelectColor) (-3).dp else 0.dp,
                                alpha = if (isSelectColor) 1.0f else 0.7f
                            )
                            .clickable {
                                scope.launch {
                                    noteBackgroundAnimatable.animateTo(
                                        targetValue = Color(colorInt),
                                        animationSpec = tween(
                                            durationMillis = 500
                                        )
                                    )
                                    colorState.value = colorInt
                                }
                                viewModel.onEvent(AddEditNoteEvent.ChangeColor(colorInt))
                            }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 标题和内容
            TransparentHintTextField(
                text = titleState.value.text,
                hint = titleState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.PutTitle(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                },
                isHintVisible = titleState.value.isHintVisible,
                singleLine = true,
                textStyle = MaterialTheme.typography.h5
            )

            drawLine(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp, vertical = 16.dp)
            )

            TransparentHintTextField(
                text = contentState.value.text,
                hint = contentState.value.hint,
                onValueChange = {
                    viewModel.onEvent(AddEditNoteEvent.PutContent(it))
                },
                onFocusChange = {
                    viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                },
                isHintVisible = contentState.value.isHintVisible,
                textStyle = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}


/**
 * 自定义阴影实现
 * */
fun Modifier.advancedShadow(
    color: Color = Color.Black,
    alpha: Float = 0f,
    cornersRadius: Dp = 0.dp,
    shadowBlurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp
) = drawBehind {

    val shadowColor = color.copy(alpha = alpha).toArgb()
    val transparentColor = color.copy(alpha = 0f).toArgb()

    drawIntoCanvas {
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowBlurRadius.toPx(),
            offsetX.toPx(),
            offsetY.toPx(),
            shadowColor
        )
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            cornersRadius.toPx(),
            cornersRadius.toPx(),
            paint
        )
    }
}

@Composable
fun drawLine(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier, onDraw = {
        drawLine(
            start = Offset(0f, size.height),
            end = Offset(size.width, size.height),
            color = Color.Gray,
            strokeWidth = 6f,
            pathEffect = PathEffect.cornerPathEffect(4.0f)
        )
    })
}