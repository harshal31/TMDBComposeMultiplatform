package com.compose.starter.features.movieDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compose.starter.commonUi.CircleIcon
import com.compose.starter.commonUi.CleanContent
import com.compose.starter.commonUi.listItemVisibility
import com.compose.starter.commonUi.stringItemVisibility
import com.compose.starter.constants.ContentDescription
import com.compose.starter.di.ShareMediaData
import com.compose.starter.features.homeScreen.HomeScreenEvent
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.KeywordsSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.MediaSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.MovieDetailInformation
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.OverviewSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.RecommendationsSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.ReviewSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.SimilarSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.TopBilledCastsSection
import com.compose.starter.navGraphs.MovieNavigation
import com.compose.starter.networking.Parameter
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import composestarter.composeapp.generated.resources.fav_filled
import composestarter.composeapp.generated.resources.fav_unfilled
import org.koin.compose.koinInject

@Composable
fun MovieDetailScreen(
    padding: PaddingValues,
    id: String,
    viewModel: MovieDetailScreenModel,
    navigateToDetail: (MovieNavigation) -> Unit,
    onEvent: (HomeScreenEvent) -> Unit,
    onStatusBarColorChange: (Color?) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.mediaDetailUiState.collectAsStateWithLifecycle()
    var dominantColor by remember { mutableStateOf<Color?>(null) }
    val shareMediaData = koinInject<ShareMediaData>()

    LaunchedEffect(Unit) {
        onEvent(HomeScreenEvent.HideAppBar)
        viewModel.fetchInitialMovieDetailData(id)
    }

    LaunchedEffect(dominantColor) {
        onStatusBarColorChange(dominantColor)
    }

    LaunchedEffect(uiState.movieDetail?.id) {
        shareMediaData.updateMovieState(uiState)
    }

    CleanContent(
        padding = padding,
        apiState = uiState.apiState,
        onBack = onBack,
        onRetry = { viewModel.fetchInitialMovieDetailData(id) }
    ) { pad ->
        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(pad)
            ) {
                item(
                    key = 0
                ) {
                    MovieDetailInformation(
                        mediaDetail = uiState.movieDetail,
                        rating = uiState.rating,
                        certification = uiState.certification,
                        releaseYear = uiState.releaseYear,
                        shouldAddToWatchList = uiState.shouldAddToWatchList,
                        sessionId = uiState.sessionId,
                        accountId = uiState.accountId,
                        onEvent = viewModel::onEvent,
                        onDominantColor = {
                            dominantColor = it
                        }
                    )
                }

                item(
                    key = 1
                ) {
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                }

                stringItemVisibility(
                    key = 2,
                    value = uiState.movieDetail?.overview
                ) {
                    OverviewSection(
                        title = uiState.movieDetail?.title,
                        overview = it,
                        posterPath = uiState.movieDetail?.backdropPath,
                        importantCrewMap = uiState.importantCrewMap,
                        overviewPairs = uiState.overviewPairs
                    )
                }

                item(key = 3) {
                    MediaSection(
                        uiState.movieDetail?.videos?.results,
                        uiState.movieDetail?.images?.posters,
                        uiState.movieDetail?.images?.backdrops,
                    ) {

                    }
                }

                listItemVisibility(
                    key = 4,
                    value = uiState.movieDetail?.credits?.cast
                ) {
                    TopBilledCastsSection(
                        casts = it.take(9),
                        navigateToDetail = navigateToDetail
                    )
                }

                listItemVisibility(
                    key = 5,
                    value = uiState.movieDetail?.reviews?.results
                ) {
                    ReviewSection(it.first())
                }

                listItemVisibility(
                    key = 6,
                    value = uiState.movieDetail?.recommendations?.results
                ) {
                    RecommendationsSection(it)
                }

                listItemVisibility(
                    key = 7,
                    value = uiState.movieDetail?.similar?.results
                ) {
                    SimilarSection(it)
                }

                listItemVisibility(
                    key = 8,
                    value = uiState.movieDetail?.keywords?.keywords
                ) {
                    KeywordsSection(it)
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
private fun NavigationRow(
    onBack: () -> Unit,
    mediaId: String?,
    sessionId: String?,
    accountId: String?,
    isFavorite: Boolean,
    onEvent: (MediaDetailUiEvent) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
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