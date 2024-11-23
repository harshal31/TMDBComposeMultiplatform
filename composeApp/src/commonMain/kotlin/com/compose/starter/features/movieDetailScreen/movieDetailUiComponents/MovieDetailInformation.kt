package com.compose.starter.features.movieDetailScreen.movieDetailUiComponents

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.compose.starter.commonUi.DominantColorCoilImage
import com.compose.starter.commonUi.StarRatingBar
import com.compose.starter.constants.AppConstants
import com.compose.starter.constants.ContentDescription
import com.compose.starter.features.movieDetailScreen.MediaDetailUiEvent
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.MappedMovieDetail
import com.compose.starter.screenHeight
import com.compose.starter.spacingsAndBorders.sizing
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.bookmark_filled
import composestarter.composeapp.generated.resources.bookmark_unfilled
import composestarter.composeapp.generated.resources.percent
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

@Composable
fun MovieDetailInformation(
    mediaDetail: MappedMovieDetail?,
    certification: String?,
    releaseYear: String?,
    rating: Int,
    shouldAddToWatchList: Boolean,
    sessionId: String? = null,
    accountId: String? = null,
    onDominantColor: (Color?) -> Unit,
    onEvent: (MediaDetailUiEvent) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6f),
    ) {
        DominantColorCoilImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.5f)
                .drawWithCache {
                    val gradient = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            Color.Black,
                        ),
                        startY = size.height * 0.2f,
                        endY = size.height
                    )
                    onDrawWithContent {
                        drawContent()
                        drawRect(gradient, blendMode = BlendMode.Multiply)
                    }
                },
            url = AppConstants.FULL_SIZE_BASE_URL + (mediaDetail?.backdropPath
                ?: mediaDetail?.posterPath),
            contentDesc = "",
            placeHolder = null,
            scale = ContentScale.Crop,
            onDominantColor = onDominantColor
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .align(Alignment.BottomCenter)
                .padding(horizontal = MaterialTheme.spacing.default)
                .padding(bottom = MaterialTheme.spacing.small),
        ) {
            mediaDetail?.title?.let {
                Text(
                    it,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                    ),
                    color = Color.White
                )
            }
            Spacer(Modifier.height(MaterialTheme.spacing.extraSmall))
            MediaInfoRow(
                mediaId = mediaDetail?.id.toString(),
                ratedValue = certification,
                releaseYear = releaseYear,
                shouldAddToWatchList = shouldAddToWatchList,
                sessionId = sessionId,
                accountId = accountId,
                onEvent = onEvent
            )
            LazyRow(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                mediaDetail?.genres?.let {
                    items(it.items, key = { v -> v.id ?: 0 }) { genre ->
                        genre.name?.let {
                            SuggestionChip(
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .padding(end = MaterialTheme.spacing.extraSmall),
                                onClick = {

                                },
                                label = {
                                    Text(
                                        it,
                                        style = MaterialTheme.typography.labelMedium,
                                        textAlign = TextAlign.Center,
                                        color = Color.White
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {

                mediaDetail?.voteAverage?.let {
                    Box(
                        modifier = Modifier.wrapContentSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(MaterialTheme.sizing.massive),
                            progress = {
                                it.toFloat() * 0.1f
                            },
                            strokeWidth = MaterialTheme.sizing.medium,
                            color = mediaDetailFillColor,
                            trackColor = MaterialTheme.colorScheme.outline

                        )
                        Text(
                            stringResource(Res.string.percent, (it * 10).roundToInt()),
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            textAlign = TextAlign.Center,
                            color = Color.White
                        )
                    }
                }

                VerticalDivider(
                    modifier = Modifier.fillMaxHeight()
                        .padding(
                            horizontal = MaterialTheme.spacing.small,
                            vertical = MaterialTheme.spacing.small
                        ),
                    color = MaterialTheme.colorScheme.outline
                )

                StarRatingBar(
                    rating = rating,
                    onRatingChanged = {
                        onEvent(
                            MediaDetailUiEvent.OnRatingChanged(
                                it,
                                mediaDetail?.id.toString(),
                                sessionId
                            )
                        )
                    }
                )
            }
        }
    }
}

@Composable
private fun MediaInfoRow(
    mediaId: String,
    ratedValue: String?,
    releaseYear: String?,
    shouldAddToWatchList: Boolean,
    sessionId: String?,
    accountId: String?,
    onEvent: (MediaDetailUiEvent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ratedValue?.let {
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .border(
                        width = MaterialTheme.sizing.nano,
                        color = Color.Gray,
                        shape = RoundedCornerShape(MaterialTheme.sizing.small)
                    )
                    .padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.extraSmall
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    it,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }?.also {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight()
                    .padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.extraSmall
                    ),
                color = MaterialTheme.colorScheme.outline
            )
        }

        releaseYear?.let {
            Text(
                it,
                modifier = Modifier
                    .wrapContentWidth()
                    .fillMaxHeight()
                    .padding(start = MaterialTheme.spacing.extraSmall),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }?.also {
            VerticalDivider(
                modifier = Modifier.fillMaxHeight()
                    .padding(
                        horizontal = MaterialTheme.spacing.small,
                        vertical = MaterialTheme.spacing.extraSmall
                    ),
                color = MaterialTheme.colorScheme.outline
            )
        }

        IconButton(
            modifier = Modifier.size(MaterialTheme.sizing.large),
            onClick = {
                onEvent(
                    MediaDetailUiEvent.OnAddToWatchList(
                        mediaType = Parameter.Media.MOVIE.value,
                        shouldAdd = !shouldAddToWatchList,
                        mediaId = mediaId,
                        sessionId = sessionId,
                        accountId = accountId
                    )
                )
            },
        ) {
            Icon(
                modifier = Modifier.size(MaterialTheme.sizing.large),
                painter = painterResource(if (shouldAddToWatchList) Res.drawable.bookmark_filled else Res.drawable.bookmark_unfilled),
                contentDescription = ContentDescription.watchlist(shouldAddToWatchList),
                tint = if (shouldAddToWatchList) mediaDetailFillColor else Color.White
            )
        }
    }
}
