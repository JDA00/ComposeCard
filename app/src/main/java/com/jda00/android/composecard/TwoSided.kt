package com.jda00.android.composecard

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs

enum class TWOSIDED {
    HORIZONTAL, VERTICAL,
}

@Composable
fun DoubleSide(
    rotX: Float = 0f,
    rotY: Float = 0f,
    rotZ: Float = 0f,
    transX: Float = 0f,
    transY: Float = 0f,
    camDist: Float = 8f,
    flipType: TWOSIDED,
    front: @Composable () -> Unit,
    back: @Composable () -> Unit
) {
    fun isHorizFlip() = (abs(rotX) % 360 > 90f && abs(rotX) % 360 < 270f)
    fun isVertFlip() = (abs(rotY) % 360 > 90f && abs(rotY) % 360 < 270f)


    fun isFlipped() = isVertFlip() xor isHorizFlip()

    if (isFlipped()) {
        val rotXBack =
            if (flipType == TWOSIDED.HORIZONTAL)
                rotX - 180
            else
                rotX

        val rotYBack =
            if (flipType == TWOSIDED.VERTICAL)
                rotY - 180
            else
                -rotY
        Box(
            Modifier
                .graphicsLayer(
                    translationX = transX,
                    translationY = transY,
                    rotationX = rotXBack,
                    rotationY = rotYBack,
                    rotationZ = -rotZ,
                    cameraDistance = camDist
                )
        ) {
            back()
        }
    } else {
        Box(
            Modifier
                .graphicsLayer(
                    translationX = transX,
                    translationY = transY,
                    rotationX = rotX,
                    rotationY = rotY,
                    rotationZ = rotZ,
                    cameraDistance = camDist
                )
        ) {
            front()
        }
    }
}