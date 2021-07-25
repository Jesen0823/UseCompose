package com.example.ui_abstraction.constraint

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainScope
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.ui_abstraction.ui.theme.LayoutsCodeTheme

@Composable
fun ConstraintLayoutContent() {
    ConstraintLayout {

        // Create references for the composables to constrain
        val (button, button2, text) = createRefs()

        Button(
            onClick = { /* Do something */ },
            // Assign reference "button" to the Button composable
            // and constrain it to the top of the ConstraintLayout
            modifier = Modifier.constrainAs(button) {
                top.linkTo(parent.top, margin = 16.dp)
            }
        ) {
            Text("Button")
        }

        // Assign reference "text" to the Text composable
        // and constrain it to the bottom of the Button composable
        Text("Text", Modifier.constrainAs(text) {
            top.linkTo(button.bottom, margin = 16.dp)
            //centerHorizontallyTo(parent)
            centerAround(button.end)
        })

        val barrier = createEndBarrier(button, text)
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.constrainAs(button2) {
                top.linkTo(parent.top, margin = 16.dp)
                start.linkTo(barrier)
            }
        ) {
            Text(
                "Button 2",
                fontSize = TextUnit.Unspecified
            )
        }
    }
}

@Composable
fun LargeConstraintLayout() {
    ConstraintLayout() {
        val text = createRef()
        val guideLine = createGuidelineFromStart(fraction = 0.5f)
        Text(
            "a long text how long , very long, this Text",
            Modifier.constrainAs(text) {
                linkTo(start = guideLine, end = parent.end)
                /*
                [preferredWrapContent] - the layout is wrap content, subject to the constraints
                in that dimension.
                [wrapContent] - the layout is wrap content even if the constraints would not allow it.
                [fillToConstraints] - the layout will expand to fill the space defined by its constraints in that dimension.
                [preferredValue] - the layout is a fixed dp value, subject to the constraints in that dimension.
                [value] - the layout is a fixed dp value, regardless of the constraints in that dimension
                 */
                width = Dimension.preferredWrapContent
            }
        )
    }
}

@Composable
fun DecoupledConstraintLayout(){
    BoxWithConstraints() {
        val constraints = if (maxWidth < maxHeight){
            decoupledConstraints(margin = 16.dp)
        }else{
            decoupledConstraints(margin = 32.dp)
        }
        ConstraintLayout(constraints) {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.layoutId("button")
                ) {
                Text(text = "Button")
            }
            Text("Text", Modifier.layoutId("text"))
        }
    }
}

private fun decoupledConstraints(margin: Dp): ConstraintSet {
    return ConstraintSet {
        val button = createRefFor("button")
        val text = createRefFor("text")

        constrain(button){
            top.linkTo(parent.top, margin = margin)
        }

        constrain(text){
            top.linkTo(button.bottom, margin = margin)
        }
    }
}

@Preview
@Composable
fun ConstraintLayoutContentPreview() {
    LayoutsCodeTheme {
        ConstraintLayoutContent()
        LargeConstraintLayout()
        Spacer(modifier = Modifier.height(20.dp))
        DecoupledConstraintLayout()
    }
}



