package com.compose.starter.spacingsAndBorders

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private data object SizingDefaults {
    const val NONE = 0
    const val NANO = 1
    const val TINY = 2
    const val SMALL = 4
    const val MEDIUM = 6
    const val LARGE = 24
    const val EXTRA_LARGE = 30
    const val GIANT = 40
    const val MASSIVE = 50
    const val ONE_FORTY = 140
    const val LARGE_TILE_WIDTH = 150
    const val LARGE_TILE_HEIGHT = 225
    const val LARGE_IMAGE_SIZE = 130
    const val MEDIUM_IMAGE_SIZE = 110
    const val FIFTY_FIVE = 55
    const val TWENTY = 20
    const val ONE_TWENTY = 120
    const val TWO_HUNDRED = 200
    const val TWO_TWENTY = 220
    const val TWO_TEN = 210
    const val THREE_HUNDRED = 300
    const val TWO_FIFTY = 250
    const val TWO_SIXTY = 260
    const val TWO_SEVENTY = 270
    const val TWO_EIGHTY = 280
    const val SIXTY = 60
    const val EIGHTY = 80
    const val HUNDRED = 100
    const val ONE_EIGHTY = 180
    const val ONE_SEVENTY = 170
    const val ONE_SIXTY = 160
    const val TWELVE = 12
    const val THIRTEEN = 13
    const val FOURTEEN = 14
    const val SIXTEEN = 16
    const val EIGHTEEN = 18
}

data class Sizing(
    val none: Dp = SizingDefaults.NONE.dp,
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
    val fiftyFive: Dp = SizingDefaults.FIFTY_FIVE.dp,
    val hundred: Dp = SizingDefaults.HUNDRED.dp,
    val twoHundred: Dp = SizingDefaults.TWO_HUNDRED.dp,
    val threeHundred: Dp = SizingDefaults.THREE_HUNDRED.dp,
    val twoFifty: Dp = SizingDefaults.TWO_FIFTY.dp,
    val twoSixty: Dp = SizingDefaults.TWO_SIXTY.dp,
    val twoSeventy: Dp = SizingDefaults.TWO_SEVENTY.dp,
    val twoEighty: Dp = SizingDefaults.TWO_EIGHTY.dp,
    val oneEighty: Dp = SizingDefaults.ONE_EIGHTY.dp,
    val oneSeventy: Dp = SizingDefaults.ONE_SEVENTY.dp,
    val oneSixty: Dp = SizingDefaults.ONE_SIXTY.dp,
    val sixty: Dp = SizingDefaults.SIXTY.dp,
    val eighty: Dp = SizingDefaults.EIGHTY.dp,
    val oneForty: Dp = SizingDefaults.ONE_FORTY.dp,
    val twenty: Dp = SizingDefaults.TWENTY.dp,
    val oneTwenty: Dp = SizingDefaults.ONE_TWENTY.dp,
    val twoTwenty: Dp = SizingDefaults.TWO_TWENTY.dp,
    val twoTen: Dp = SizingDefaults.TWO_TEN.dp,

    val twelve: Dp = SizingDefaults.TWELVE.dp,
    val thirteen: Dp = SizingDefaults.THIRTEEN.dp,
    val fourteen: Dp = SizingDefaults.FOURTEEN.dp,
    val sixteen: Dp = SizingDefaults.SIXTEEN.dp,
    val eighteen: Dp = SizingDefaults.EIGHTEEN.dp,
)

val LocalSizing = compositionLocalOf { Sizing() }


val MaterialTheme.sizing: Sizing
    @Composable
    @ReadOnlyComposable
    get() = LocalSizing.current

