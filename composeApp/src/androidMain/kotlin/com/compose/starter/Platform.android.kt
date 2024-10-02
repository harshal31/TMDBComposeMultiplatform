package com.compose.starter

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import coil3.Bitmap
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.bitmapConfig
import coil3.request.crossfade
import android.graphics.Bitmap as AndroidBitmap

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

@Composable
actual fun SetStatusBarColor(color: Color, isDarkTheme: Boolean) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window.apply {
                statusBarColor = color.toArgb()
                navigationBarColor = color.toArgb()
            }
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDarkTheme
                isAppearanceLightNavigationBars = !isDarkTheme
            }
        }
    }
}


actual val screenWidth: Dp
    @Composable
    get() = LocalConfiguration
        .current
        .screenWidthDp
        .dp

actual val screenHeight: Dp
    @Composable
    get() = LocalConfiguration
        .current
        .screenHeightDp
        .dp


@SuppressLint("NewApi")
actual fun Bitmap.toImageBitmap(): ImageBitmap {
    val androidBitmap = this
    if (androidBitmap.config == AndroidBitmap.Config.HARDWARE) {
        val softwareBitmap = androidBitmap.copy(AndroidBitmap.Config.ARGB_8888, false)
        return softwareBitmap.asImageBitmap()
    }
    return androidBitmap.asImageBitmap()
}


actual fun platformImageRequest(
    context: PlatformContext,
    url: String,
): ImageRequest {
    return ImageRequest
        .Builder(context)
        .data(url)
        .diskCachePolicy(CachePolicy.ENABLED)
        .bitmapConfig(AndroidBitmap.Config.ARGB_8888)
        .crossfade(true)
        .build()
}