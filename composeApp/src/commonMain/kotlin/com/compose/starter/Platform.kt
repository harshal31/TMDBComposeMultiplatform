package com.compose.starter

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.Dp
import coil3.Bitmap
import coil3.PlatformContext
import coil3.request.ImageRequest

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


@Composable
expect fun SetStatusBarColor(color: Color, isDarkTheme: Boolean)

@get:Composable
expect val screenWidth: Dp

@get:Composable
expect val screenHeight: Dp

expect fun Bitmap.toImageBitmap(): ImageBitmap

expect fun platformImageRequest(context: PlatformContext, url: String): ImageRequest