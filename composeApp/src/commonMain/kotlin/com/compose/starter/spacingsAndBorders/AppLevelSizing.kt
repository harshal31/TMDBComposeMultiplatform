package com.compose.starter.spacingsAndBorders

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private data object SizingDefaults {
    const val NANO = 1
    const val TINY = 2
    const val SMALL = 4
    const val MEDIUM = 6
    const val LARGE = 24
    const val EXTRA_LARGE = 30
    const val GIANT = 40
    const val MASSIVE = 50
    const val LARGE_TILE_WIDTH = 150
    const val LARGE_TILE_HEIGHT = 225
    const val LARGE_IMAGE_SIZE = 130
    const val MEDIUM_IMAGE_SIZE = 110
}

data class Sizing(
    val nano: Dp = SizingDefaults.NANO.dp,
    val tiny: Dp = SizingDefaults.TINY.dp,
    val small: Dp = SizingDefaults.SMALL.dp,
    val medium: Dp = SizingDefaults.MEDIUM.dp,
    val large: Dp = SizingDefaults.LARGE.dp,
    val extraLarge: Dp = SizingDefaults.EXTRA_LARGE.dp,
    val giant: Dp = SizingDefaults.GIANT.dp,
    val massive: Dp = SizingDefaults.MASSIVE.dp,
    val largeTileWidth: Dp = SizingDefaults.LARGE_TILE_WIDTH.dp,
    val largeTileHeight: Dp = SizingDefaults.LARGE_TILE_HEIGHT.dp,
    val largeImageSize: Dp = SizingDefaults.LARGE_IMAGE_SIZE.dp,
    val mediumImageSize: Dp = SizingDefaults.MEDIUM_IMAGE_SIZE.dp,
)

val LocalSizing = compositionLocalOf { Sizing() }


val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current

