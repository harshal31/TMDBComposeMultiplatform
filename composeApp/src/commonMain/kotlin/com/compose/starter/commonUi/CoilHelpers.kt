package com.compose.starter.commonUi

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.toBitmap
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.platformImageRequest
import com.compose.starter.toImageBitmap
import com.kmpalette.color
import com.kmpalette.palette.graphics.Palette
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.image_error_placeholder
import composestarter.composeapp.generated.resources.image_loader_placeholder
import org.jetbrains.compose.resources.painterResource

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDesc: String = "",
    scale: ContentScale = ContentScale.Fit,
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)

            .crossfade(true)
            .build(),
        onSuccess = { suc ->
            suc.result.image.toBitmap()
        },
        contentDescription = contentDesc,
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = scale,
    )
}


@Composable
fun DominantColorCoilImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDesc: String = "",
    scale: ContentScale = ContentScale.Fit,
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
    onDominantColor: (Color?) -> Unit,
) {
    AsyncImage(
        modifier = modifier,
        model = platformImageRequest(LocalPlatformContext.current, url),
        onSuccess = {
            val swatch =
                Palette.from(it.result.image.toBitmap().toImageBitmap()).generate().dominantSwatch
            onDominantColor(swatch?.color)
        },
        contentDescription = contentDesc,
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = scale,
    )
}


@Composable
fun TmdbCropSizeImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentName: String = "",
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .data(AppConstants.CROP_SIZE_BASE_URL + url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = ContentDescription.contentImage(contentName),
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = contentScale,
    )
}


@Composable
fun TmdbFullSizeImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentName: String = "",
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .data(AppConstants.FULL_SIZE_BASE_URL + url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = ContentDescription.contentImage(contentName),
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = contentScale,
    )
}

