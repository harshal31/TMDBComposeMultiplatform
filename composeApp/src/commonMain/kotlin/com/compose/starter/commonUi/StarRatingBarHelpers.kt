package com.compose.starter.commonUi

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.custom_star
import org.jetbrains.compose.resources.painterResource

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Int = 0,
    filledColor: Color = mediaDetailFillColor,
    starSize: Dp = MaterialTheme.sizing.extraLarge,
    starSpacing: Dp = MaterialTheme.sizing.small,
    onRatingChanged: (Int) -> Unit,
) {
    var mRating by remember { mutableStateOf(rating) }

    Box(
        modifier = Modifier.wrapContentWidth(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .selectableGroup()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val starTotalWidth =
                            (starSize.toPx() + starSpacing.toPx()) * maxStars - starSpacing.toPx()
                        val clickedPosition = (offset.x / starTotalWidth) * maxStars
                        val clickedStar = (clickedPosition + 1).toInt()
                            .coerceIn(1, maxStars) // Rounding and coerce to correct range
                        mRating = clickedStar
                        onRatingChanged(mRating)
                    }
                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = MaterialTheme.colorScheme.outline
            for (i in 1..maxStars) {
                val isFilled = i <= mRating
                val painter = painterResource(Res.drawable.custom_star)

                Canvas(
                    modifier = Modifier
                        .size(starSize)
                        .padding(starSpacing / 2)
                ) {
                    with(painter) {
                        draw(
                            size = size,
                            colorFilter = ColorFilter.tint(color)
                        )
                    }

                    if (isFilled) {
                        with(painter) {
                            draw(
                                size = size,
                                colorFilter = ColorFilter.tint(filledColor)
                            )
                        }
                    }
                }

                if (i < maxStars) {
                    Spacer(modifier = Modifier.width(starSpacing))
                }
            }
        }
    }
}


/**
 * Fractional start rating handle
@Composable
fun StarRatingBar(
maxStars: Int = 5,
rating: Float = 0f,
starSize: Dp = Sizing.thirty,
starSpacing: Dp = Sizing.four,
onRatingChanged: (Float) -> Unit,
) {
var mRating by remember { mutableStateOf(rating) }

Box(
modifier = Modifier.fillMaxWidth(),
contentAlignment = Alignment.Center
) {
Row(
modifier = Modifier
.selectableGroup()
.pointerInput(Unit) {
detectTapGestures { offset ->
val totalWidth =
(maxStars * (starSize.toPx() + starSpacing.toPx())) - starSpacing.toPx()
val clickedFraction = (offset.x / totalWidth) * maxStars
mRating = clickedFraction.coerceIn(0f, maxStars.toFloat())
onRatingChanged(mRating)
}
},
verticalAlignment = Alignment.CenterVertically
) {
for (i in 1..maxStars) {
// Calculate the fractional value for the current star
val fraction = (mRating - i + 1).coerceIn(0f, 1f)
val painter =
painterResource(Res.drawable.custom_star)

Canvas(
modifier = Modifier
.size(starSize)
.padding(starSpacing / 2)
) {


with(painter) {
draw(
size = size,
colorFilter = ColorFilter.tint(Color.Gray)
)
}

clipRect(right = size.width * fraction) {
with(painter) {
draw(
size = size,
colorFilter = ColorFilter.tint(Color(0xFFFFC700))
)
}
}
}

if (i < maxStars) {
Spacer(modifier = Modifier.width(starSpacing))
}
}
}
}
}*/
