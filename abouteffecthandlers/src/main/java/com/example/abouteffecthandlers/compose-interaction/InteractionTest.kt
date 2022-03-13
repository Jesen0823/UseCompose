package com.example.abouteffecthandlers.compose

/**
 * DragInteraction 与拖拽事件相关的交互。
 * FocusInteraction与焦点事件相关的交互。
 * HoverInteraction 与悬停事件相关的交互。
 * Interaction 交互表示组件的临时UI状态，通常与组件可能控制的实际“业务”状态分开。
 * InteractionSource  InteractionSource表示与组件发出的事件相对应的交互流。
 * MutableInteractionSource MutableInteractionSource表示与组件发出的事件相对应的交互流。
 * PressInteraction  与触摸事件相关的互动。
 *
 * */
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.consumePositionChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DragInteractionTest() {

    // Draw a seekbar-like composable that has a black background
    // with a red square that moves along the 300.dp drag distance
    val max = 300.dp
    val min = 0.dp
    val (minPx, maxPx) = with(LocalDensity.current) { min.toPx() to max.toPx() }
    // this is the  state we will update while dragging
    val offsetPosition = remember { mutableStateOf(0f) }

    // seekbar itself
    Box(
        modifier = Modifier
            .width(max)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    val newValue = offsetPosition.value + delta
                    offsetPosition.value = newValue.coerceIn(minPx, maxPx)
                }
            )
            .background(Color.Black)
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetPosition.value.roundToInt(), 0) }
                .size(50.dp)
                .background(Color.Red)
        )
    }
}

@Composable
fun HorizontalDragTest() {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    var width by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .fillMaxSize()
            .onSizeChanged { width = it.width.toFloat() }
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                .fillMaxHeight()
                .width(50.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            val down = awaitFirstDown()
                            val change =
                                awaitHorizontalTouchSlopOrCancellation(down.id) { change, over ->
                                    val originalX = offsetX.value
                                    val newValue =
                                        (originalX + over).coerceIn(0f, width - 50.dp.toPx())
                                    change.consumePositionChange()
                                    offsetX.value = newValue
                                }
                            if (change != null) {
                                horizontalDrag(change.id) {
                                    val originalX = offsetX.value
                                    val newValue = (originalX + it.positionChange().x)
                                        .coerceIn(0f, width - 50.dp.toPx())
                                    it.consumePositionChange()
                                    offsetX.value = newValue
                                }
                            }
                        }
                    }
                }
        )
    }

}

@Composable
fun ScrollableTest() {
    // actual composable state that we will show on UI and update in `Scrollable`
    val offset = remember { mutableStateOf(0f) }
    Box(
        Modifier
            .size(150.dp)
            .scrollable(
                orientation = Orientation.Vertical,
                // state for Scrollable, describes how consume scroll amount
                state = rememberScrollableState { delta ->
                    // use the scroll data and indicate how much this element consumed.
                    // unconsumed deltas will be propagated to nested scrollables (if present)
                    offset.value = offset.value + delta // update the state
                    delta // indicate that we consumed all the pixels available
                }
            )
            .background(Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        // Modifier.scrollable is not opinionated about its children's layouts. It will however
        // promote nested scrolling capabilities if those children also use the modifier.
        // The modifier will not change any layouts so one must handle any desired changes through
        // the delta values in the scrollable state
        Text(offset.value.roundToInt().toString(), style = TextStyle(fontSize = 32.sp))
    }
}

@Composable
fun TransformableTest() {
    Box(
        Modifier
            .size(200.dp)
            .clipToBounds()
            .background(Color.LightGray)
    ) {
        // set up all transformation states
        var scale by remember { mutableStateOf(1f) }
        var rotation by remember { mutableStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val coroutineScope = rememberCoroutineScope()
        // let's create a modifier state to specify how to update our UI state defined above
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            // note: scale goes by factor, not an absolute difference, so we need to multiply it
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange
        }
        Box(
            Modifier
                // apply pan offset state as a layout transformation before other modifiers
                .offset { IntOffset(offset.x.roundToInt(), offset.y.roundToInt()) }
                // add transformable to listen to multitouch transformation events after offset
                .transformable(state = state)
                // optional for example: add double click to zoom
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = {
                            coroutineScope.launch { state.animateZoomBy(4f) }
                        }
                    )
                }
                .fillMaxSize()
                .border(1.dp, Color.Green),
            contentAlignment = Alignment.Center
        ) {
            Text(
                "\uD83C\uDF55",
                fontSize = 32.sp,
                // apply other transformations like rotation and zoom on the pizza slice emoji
                modifier = Modifier.graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation
                )
            )
        }
    }
}

@Composable
fun VerticalDragTest() {
    val offsetX = remember { mutableStateOf(0f) }
    val offsetY = remember { mutableStateOf(0f) }
    var height by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .fillMaxSize()
            .onSizeChanged { height = it.height.toFloat() }
    ) {
        Box(
            Modifier
                .offset { IntOffset(offsetX.value.roundToInt(), offsetY.value.roundToInt()) }
                .fillMaxWidth()
                .height(50.dp)
                .background(Color.Blue)
                .pointerInput(Unit) {
                    forEachGesture {
                        awaitPointerEventScope {
                            val down = awaitFirstDown()
                            val change =
                                awaitVerticalTouchSlopOrCancellation(down.id) { change, over ->
                                    val originalY = offsetY.value
                                    val newValue = (originalY + over)
                                        .coerceIn(0f, height - 50.dp.toPx())
                                    change.consumePositionChange()
                                    offsetY.value = newValue
                                }
                            if (change != null) {
                                verticalDrag(change.id) {
                                    val originalY = offsetY.value
                                    val newValue = (originalY + it.positionChange().y)
                                        .coerceIn(0f, height - 50.dp.toPx())
                                    it.consumePositionChange()
                                    offsetY.value = newValue
                                }
                            }
                        }
                    }
                }
        )
    }
}