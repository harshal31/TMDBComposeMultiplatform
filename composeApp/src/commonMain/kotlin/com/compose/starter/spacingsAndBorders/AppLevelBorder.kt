package com.compose.starter.spacingsAndBorders

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.dp


private data object BorderDefaults {
    const val NANO = 1
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val MED_LARGE = 24
    const val LARGE = 32
    const val EXTRA_LARGE = 50
    const val DEFAULT = MEDIUM
}


data class RectangularBorder(
    val default: RoundedCornerShape = RoundedCornerShape(BorderDefaults.DEFAULT.dp),
    val nano: RoundedCornerShape = RoundedCornerShape(BorderDefaults.NANO.dp),
    val tiny: RoundedCornerShape = RoundedCornerShape(BorderDefaults.TINY.dp),
    val extraSmall: RoundedCornerShape = RoundedCornerShape(BorderDefaults.EXTRA_SMALL.dp),
    val medLarge: RoundedCornerShape = RoundedCornerShape(BorderDefaults.MED_LARGE.dp),
    val small: RoundedCornerShape = RoundedCornerShape(BorderDefaults.SMALL.dp),
    val medium: RoundedCornerShape = RoundedCornerShape(BorderDefaults.MEDIUM.dp),
    val large: RoundedCornerShape = RoundedCornerShape(BorderDefaults.LARGE.dp),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(BorderDefaults.EXTRA_LARGE.dp),
    val topLevelMedLargeBottomSheetBorder: RoundedCornerShape = RoundedCornerShape(
        topStart = BorderDefaults.MED_LARGE.dp,
        topEnd = BorderDefaults.MED_LARGE.dp
    ),
    val topLevelMediumBottomSheetBorder: RoundedCornerShape = RoundedCornerShape(
        topStart = BorderDefaults.MED_LARGE.dp,
        topEnd = BorderDefaults.MED_LARGE.dp
    ),

    val bottomLevelMediumBorder: RoundedCornerShape = RoundedCornerShape(
        bottomStart = BorderDefaults.MEDIUM.dp,
        bottomEnd = BorderDefaults.MEDIUM.dp
    ),
)


data class CircularBorder(
    val default: RoundedCornerShape = RoundedCornerShape(BorderDefaults.DEFAULT),
    val nano: RoundedCornerShape = RoundedCornerShape(BorderDefaults.NANO),
    val tiny: RoundedCornerShape = RoundedCornerShape(BorderDefaults.TINY),
    val extraSmall: RoundedCornerShape = RoundedCornerShape(BorderDefaults.EXTRA_SMALL),
    val medLarge: RoundedCornerShape = RoundedCornerShape(BorderDefaults.MED_LARGE),
    val small: RoundedCornerShape = RoundedCornerShape(BorderDefaults.SMALL),
    val medium: RoundedCornerShape = RoundedCornerShape(BorderDefaults.MEDIUM),
    val large: RoundedCornerShape = RoundedCornerShape(BorderDefaults.LARGE),
    val extraLarge: RoundedCornerShape = RoundedCornerShape(BorderDefaults.EXTRA_LARGE),
)

val LocalRectBorder = compositionLocalOf { RectangularBorder() }

val MaterialTheme.rectBorder: RectangularBorder
    @Composable
    @ReadOnlyComposable
    get() = LocalRectBorder.current


val LocalCircularBorder = compositionLocalOf { CircularBorder() }

val MaterialTheme.circleBorder: CircularBorder
    @Composable
    @ReadOnlyComposable
    get() = LocalCircularBorder.current

