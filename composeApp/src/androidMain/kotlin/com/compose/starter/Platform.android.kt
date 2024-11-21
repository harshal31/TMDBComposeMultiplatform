package com.compose.starter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.icu.text.NumberFormat
import android.net.Uri
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.Bitmap
import coil3.PlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.bitmapConfig
import coil3.request.crossfade
import com.compose.starter.di.getKoinValue
import java.util.Locale
import android.graphics.Bitmap as AndroidBitmap


class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

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

actual fun formatCurrency(input: Int?, locale: String): String {
    val localeObj = Locale.forLanguageTag(locale)
    val currencyFormatter = NumberFormat.getCurrencyInstance(localeObj)

    return if (input == null || input == 0) {
        "-"
    } else {
        currencyFormatter.format(input)
    }
}

actual fun getDisplayLanguage(locale: String?): String {
    return locale?.let {
        Locale.forLanguageTag(it).displayLanguage
    } ?: "-"
}

actual fun openLinkInBrowser(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    getKoinValue<Context>().startActivity(intent)
}
