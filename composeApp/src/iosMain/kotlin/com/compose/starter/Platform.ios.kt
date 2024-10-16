package com.compose.starter

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
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
import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.currentLocale
import platform.Foundation.languageCode
import platform.Foundation.localizedStringForLanguageCode
import platform.UIKit.UIDevice

class IOSPlatform : Platform {
    override val name: String =
        UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()


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

actual fun formatCurrency(input: Int?, locale: String): String {
    val formatter = NSNumberFormatter()
    formatter.numberStyle = NSNumberFormatterCurrencyStyle
    val nsLocale = NSLocale(localeIdentifier = locale)
    formatter.locale = nsLocale
    return if (input == null || input == 0) {
        "-"
    } else {
        formatter.stringFromNumber(NSNumber(input)) ?: "-"
    }
}

actual fun getDisplayLanguage(locale: String?): String {
    return locale?.let {
        val nsLocale = NSLocale(localeIdentifier = locale)
        val languageCode = nsLocale.languageCode
        NSLocale.currentLocale().localizedStringForLanguageCode(languageCode) ?: languageCode
    } ?: "-"
}