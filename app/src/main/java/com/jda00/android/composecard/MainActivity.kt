package com.jda00.android.composecard

import android.animation.TimeInterpolator
import android.os.Bundle
import android.view.animation.AnticipateInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainActivityContent()
        }
    }
}

@Composable
fun MainActivityContent() {
    FlippingCard()
}

@Preview(showBackground = true)
@Composable
fun PreviewMainActivity() {
    MainActivityContent()

}

fun TimeInterpolator.toEasing() = Easing { x -> getInterpolation(x) }

@Composable
fun FlippingCard() {
    var size by remember { mutableStateOf(IntSize.Zero) }
    val padding = 50.dp
    val paddingEdge = 0.dp
    val boxDimension = 200.dp

    with(LocalDensity.current) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding, paddingEdge, padding, paddingEdge)
                .onSizeChanged {
                    size = it
                },
            verticalArrangement = Arrangement.Center
        ) {

            var enabled by remember {
                mutableStateOf(true)
            }
            val valueFloat: Float by animateFloatAsState(
                if (enabled) 0f else 1f,
                animationSpec = tween(
                    durationMillis = 5000,
                    easing = AnticipateInterpolator().toEasing()
                )
            )
            val degree = RoundedCornerShape(100.dp)

            DoubleSide(
                transX = valueFloat * (size.width - boxDimension.toPx()),
                rotY = valueFloat * 2700,
                rotZ = valueFloat * 0,
                camDist = 16f,
                flipType = TWOSIDED.HORIZONTAL,
                front = {
                    Image(
                        painterResource(R.drawable.cage),
                        contentDescription = "Bird",
                        modifier = Modifier
                            .size(boxDimension, boxDimension)
                            .border(0.dp, Color.Black, shape = degree)
                    )
                }, back = {
                    Image(
                        painterResource(R.drawable.bird),
                        contentDescription = "Cage",
                        modifier = Modifier
                            .size(boxDimension, boxDimension)
                            .border(0.dp, Color.Black, shape = degree)
                    )
                })

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { enabled = !enabled }) {
                    Text("Click")
                }
            }
        }
    }
}