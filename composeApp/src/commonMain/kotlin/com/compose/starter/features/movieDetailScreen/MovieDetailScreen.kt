package com.compose.starter.features.movieDetailScreen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CircleIcon
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.DetailImageWithMediaInfo
import com.compose.starter.commonUi.ExpandableText
import com.compose.starter.commonUi.MediaDetailTitleWithContent
import com.compose.starter.commonUi.stringItemVisibility
import com.compose.starter.constants.ContentDescription
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.networking.Parameter
import com.compose.starter.screenWidth
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import composestarter.composeapp.generated.resources.fav_filled
import composestarter.composeapp.generated.resources.fav_unfilled
import composestarter.composeapp.generated.resources.overview

@Composable
fun MovieDetailScreen(
    padding: PaddingValues,
    id: String,
    viewModel: MovieDetailScreenModel,
    onEvent: (HomeScreenEvent) -> Unit,
    onStatusBarColorChange: (Color?) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.mediaDetailUiState.collectAsStateWithLifecycle()
    var dominantColor by remember { mutableStateOf<Color?>(null) }

    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.HideAppBar)
        viewModel.fetchInitialMovieDetailData(id)
    }

    LaunchedEffect(dominantColor) {
        onStatusBarColorChange(dominantColor)
    }

    CleanContent(
        padding = padding,
        apiState = uiState.apiState,
        onBack = onBack,
        onRetry = {
            viewModel.fetchInitialMovieDetailData(id)
        }
    ) { pad ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)
            ) {
                item {
                    DetailImageWithMediaInfo(
                        mediaDetail = uiState.movieDetail,
                        rating = uiState.rating,
                        shouldAddToWatchList = uiState.shouldAddToWatchList,
                        sessionId = uiState.sessionId,
                        accountId = uiState.accountId,
                        onEvent = viewModel::onEvent,
                        onDominantColor = {
                            dominantColor = it
                        }
                    )
                }

                stringItemVisibility(value = uiState.movieDetail?.overview) {
                    OverviewSection(
                        it,
                        uiState.importantCrewMap
                    )
                }
            }
            NavigationRow(
                onBack = onBack,
                mediaId = uiState.movieDetail?.id.toString(),
                sessionId = uiState.sessionId,
                accountId = uiState.accountId,
                isFavorite = uiState.isFavorite,
                onEvent = viewModel::onEvent
            )
        }
    }
}

@Composable
private fun OverviewSection(
    overview: String,
    importantCrewMap: List<Pair<String, String>>,
) {
    MediaDetailTitleWithContent(title = Res.string.overview) {
        ExpandableText(
            style = MaterialTheme.typography.titleMedium,
            value = overview
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.small))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            importantCrewMap.forEach {
                SuggestionChip(
                    modifier = Modifier
                        .width(screenWidth * 0.4f)
                        .fillMaxHeight()
                        .padding(end = MaterialTheme.spacing.small),
                    onClick = {

                    },
                    label = {
                        MaterialTheme.spacing.medium
                        Column(
                            modifier = Modifier.padding(MaterialTheme.spacing.tiny)
                        ) {
                            Text(
                                it.first,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                it.second,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.labelSmall,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun NavigationRow(
    onBack: () -> Unit,
    mediaId: String?,
    sessionId: String?,
    accountId: String?,
    isFavorite: Boolean,
    onEvent: (MediaDetailUiEvent) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.medium
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CircleIcon(
            icon = Res.drawable.arrow_back,
            onIconClick = onBack
        )

        CircleIcon(
            icon = if (isFavorite) Res.drawable.fav_filled else Res.drawable.fav_unfilled,
            tint = if (isFavorite) mediaDetailFillColor else Color.White,
            contentDesc = ContentDescription.favorite(isFavorite),
            onIconClick = {
                onEvent(
                    MediaDetailUiEvent.OnFavorite(
                        mediaType = Parameter.Media.MOVIE.value,
                        isFavorite = isFavorite.not(),
                        mediaId = mediaId,
                        sessionId = sessionId,
                        accountId = accountId
                    )
                )
            },
        )
    }
}
