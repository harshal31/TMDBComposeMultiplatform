package com.compose.starter.commonUi

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.Dp
import com.compose.starter.spacingsAndBorders.sizing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Composable function to draw a custom circular progress UI.
 * @param progressValue The current progress value, where 0f represents no progress and 1f represents complete progress.
 * @param startAngle The starting angle for the progress arc, in degrees. 0 degrees is at the 3 o'clock position.
 * @param size The diameter of the circular progress UI.
 * @param strokeWidth The thickness of the progress stroke.
 * @param backgroundArcColor The color of the background arc that shows the full extent of the progress circle.
 * @param progressArcColor The starting color of the progress arc's gradient.
 * @param shouldAnimate If we want to make progress animated
 */
@Composable
fun CustomCircularProgressBar(
    progressValue: Float = 0f,
    startAngle: Float = 270f,
    size: Dp = MaterialTheme.sizing.massive,
    strokeWidth: Dp = MaterialTheme.sizing.medium,
    backgroundArcColor: Color = MaterialTheme.colorScheme.outline,
    progressArcColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    shouldAnimate: Boolean = false,
    animationDuration: Int = 300,
) {
    val progress = if (progressValue.isNaN()) 0f else progressValue
    val currentProgress = remember { mutableFloatStateOf(0f) }

    val animatedProgress by animateFloatAsState(
        targetValue = currentProgress.floatValue,
        animationSpec = if (shouldEnableAnimation(
                shouldAnimate,
                progress
            )
        ) tween(animationDuration) else tween(0),
        label = "Progress Animation"
    )
    LaunchedEffect(shouldAnimate, progress) {
        if (shouldEnableAnimation(shouldAnimate, progress)) {
            progressFlow(progress).collect { value ->
                currentProgress.floatValue = value
            }
        } else {
            currentProgress.floatValue = progress
        }
    }

    Canvas(
        modifier = Modifier.size(size)
    ) {
        val strokeWidthPx = strokeWidth.toPx()

        val arcSize = size.toPx() - strokeWidthPx

        drawArc(
            color = backgroundArcColor,
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
            size = Size(arcSize, arcSize),
            style = Stroke(width = strokeWidthPx)
        )

        withTransform({
            rotate(degrees = startAngle, pivot = center)
        }) {
            drawArc(
                color = progressArcColor,
                startAngle = 0f,
                sweepAngle = animatedProgress * 360,
                useCenter = false,
                topLeft = Offset(strokeWidthPx / 2, strokeWidthPx / 2),
                size = Size(arcSize, arcSize),
                style = Stroke(width = strokeWidthPx, cap = StrokeCap.Round)
            )
        }
    }
}

private fun shouldEnableAnimation(shouldAnimate: Boolean, progress: Float): Boolean {
    return shouldAnimate && progress != 0f
}

/**
 * A function that creates a Flow emitting Float values, simulating a progress animation.
 *
 * @param targetProgress The final progress value to reach, default is 1f.
 * @param step The increment for each emitted progress value, default is 0.01f.
 * @param delayTime The delay between emissions in milliseconds, default is 1L.
 * @return A Flow emitting Float values representing the progress.
 */
fun progressFlow(
    targetProgress: Float = 1f,
    step: Float = 0.01f,
    delayTime: Long = 1L,
): Flow<Float> {
    return flow {
        var progress = 0f
        while (progress <= targetProgress) {
            emit(progress)
            progress += step
            delay(delayTime)
        }
    }
}
