package com.compose.starter.spacingsAndBorders

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


private data object SpacingDefaults {
    const val NONE = 0
    const val TINY = 2
    const val EXTRA_SMALL = 4
    const val SMALL = 8
    const val MEDIUM = 16
    const val MED_LARGE = 24
    const val LARGE = 32
    const val DEFAULT = MEDIUM
}

data class Spacing(
    val none: Dp = 0.dp,
    val default: Dp = SpacingDefaults.DEFAULT.dp,
    val tiny: Dp = SpacingDefaults.TINY.dp,
    val extraSmall: Dp = SpacingDefaults.EXTRA_SMALL.dp,
    val medLarge: Dp = SpacingDefaults.MED_LARGE.dp,
    val small: Dp = SpacingDefaults.SMALL.dp,
    val medium: Dp = SpacingDefaults.MEDIUM.dp,
    val large: Dp = SpacingDefaults.LARGE.dp,
)

val LocalSpacing = compositionLocalOf { Spacing() }


val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

