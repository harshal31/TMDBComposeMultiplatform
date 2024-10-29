package com.compose.starter.commonUi

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.LocalPlatformContext
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.size.Precision
import coil3.toBitmap
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.platformImageRequest
import com.compose.starter.spacingsAndBorders.circleBorder
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.theme.avatarBack
import com.compose.starter.theme.avatarFront
import com.compose.starter.toImageBitmap
import com.compose.starter.utilities.secondOrNull
import com.kmpalette.color
import com.kmpalette.palette.graphics.Palette
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.image_error_placeholder
import composestarter.composeapp.generated.resources.image_loader_placeholder
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
fun CoilImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDesc: String = "",
    scale: ContentScale = ContentScale.Fit,
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
    errorPlaceholder: Painter? = painterResource(Res.drawable.image_error_placeholder),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = contentDesc,
        placeholder = placeHolder,
        error = errorPlaceholder,
        contentScale = scale,
    )
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun CoilDrawableImage(
    modifier: Modifier = Modifier,
    drawableName: String,
    contentDesc: String = "",
    scale: ContentScale = ContentScale.Fit
) {
    AsyncImage(
        modifier = modifier,
        model = Res.getUri("drawable/$drawableName"),
        contentDescription = contentDesc,
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
fun CoilCropSizeImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = "",
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
        contentDescription = ContentDescription.contentImage(contentDescription),
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = contentScale,
    )
}


@Composable
fun AnoCoilCropSizeImage(
    size: DpSize,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = "",
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
) {
    AsyncImage(
        modifier = Modifier.size(size),
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .size(width = size.width.value.toInt(), height = size.height.value.toInt())
            .precision(Precision.EXACT)
            .data(url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = ContentDescription.contentImage(contentDescription),
        placeholder = placeHolder,
        error = painterResource(Res.drawable.image_error_placeholder),
        contentScale = contentScale,
    )
}


@Composable
fun CoilFullSizeImage(
    modifier: Modifier = Modifier,
    url: String,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String = "",
    placeHolder: Painter? = painterResource(Res.drawable.image_loader_placeholder),
    errorPlaceholder: Painter? = painterResource(Res.drawable.image_error_placeholder),
) {
    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalPlatformContext.current)
            .data(AppConstants.FULL_SIZE_BASE_URL + url)
            .diskCachePolicy(CachePolicy.ENABLED)
            .crossfade(true)
            .build(),
        contentDescription = ContentDescription.contentImage(contentDescription),
        placeholder = placeHolder,
        error = errorPlaceholder,
        contentScale = contentScale,
    )

}


@Composable
fun AvatarElseInitialLetter(
    avatarUrl: String?,
    userName: String?,
    circleSize: Dp = MaterialTheme.sizing.largeImageSize,
    borderWidth: Dp = MaterialTheme.sizing.twenty
) {
    val imageSize = circleSize.minus(borderWidth)
    val split = userName
        ?.split(" ")
        ?.mapNotNull { it.uppercase().firstOrNull() }

    val userInitial = when (split?.size) {
        1 -> split.firstOrNull()?.toString() ?: "-"
        2 -> (split.firstOrNull()?.toString() ?: "-").plus((split.secondOrNull()?.toString() ?: "-"))
        else -> "-"
    }

    Box(
        modifier = Modifier
            .size(circleSize)
            .background(
                color = avatarBack,
                shape = MaterialTheme.circleBorder.extraLarge
            ),
        contentAlignment = Alignment.Center
    ) {
        avatarUrl?.let {
            CoilImage(
                modifier = Modifier
                    .size(imageSize)
                    .clip(MaterialTheme.circleBorder.extraLarge),
                url = AppConstants.CROP_SIZE_BASE_URL + it,
                contentDesc = ContentDescription.PROFILE_IMAGE,
                scale = ContentScale.Crop,
            )

        } ?: run {
            Box(
                modifier = Modifier
                    .size(imageSize)
                    .background(
                        color = avatarFront,
                        shape = MaterialTheme.circleBorder.extraLarge
                    )
                    .align(Alignment.Center),
                contentAlignment = Alignment.Center
            ) {
                if (userInitial.isNotEmpty()) {
                    Text(
                        userInitial,
                        style = MaterialTheme.typography.displayLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = (MaterialTheme.typography.displayLarge.fontSize.value + 13f).sp
                        ),
                        color = Color.White
                    )
                }
            }
        }
    }
}