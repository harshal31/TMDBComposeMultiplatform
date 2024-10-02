package com.compose.starter

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asComposeImageBitmap
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import coil3.Bitmap
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.compose.starter.statusBar.StatusBar
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun SetStatusBarColor(color: Color, isDarkTheme: Boolean) {
    StatusBar.setStatusBarColorWithRed(
        color.red.toDouble(),
        color.green.toDouble(),
        color.blue.toDouble(),
        color.alpha.toDouble(),
    )
}


@OptIn(ExperimentalComposeUiApi::class)
actual val screenWidth: Dp
    @Composable
    get() = with(LocalDensity.current) {
        LocalWindowInfo
            .current
            .containerSize
            .width
            .toDp()
    }

@OptIn(ExperimentalComposeUiApi::class)
actual val screenHeight: Dp
    @Composable
    get() = with(LocalDensity.current) {
        LocalWindowInfo
            .current
            .containerSize
            .height
            .toDp()
    }

actual fun Bitmap.toImageBitmap(): ImageBitmap {
    return asComposeImageBitmap()
}

actual fun platformImageRequest(
    context: PlatformContext,
    url: String,
): ImageRequest {
    return ImageRequest
        .Builder(context)
        .data(url)
        .diskCachePolicy(CachePolicy.ENABLED)
        .crossfade(true)
        .build()
}