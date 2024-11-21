package com.compose.starter.features.movieDetailScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.MoreVert
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.compose.starter.commonUi.DetailTitleWithIcon
import com.compose.starter.commonUi.statusNavModifier
import com.compose.starter.constants.ContentDescription
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.FeaturedCastRow
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.KeywordsFlowRow
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.MediaSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.MovieDetailInformation
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.OverviewSection
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.RecommendedLazyRow
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.ReviewCard
import com.compose.starter.features.movieDetailScreen.movieDetailUiComponents.SimilarLazyRow
import com.compose.starter.navGraphs.Movie
import com.compose.starter.networking.Parameter
import com.compose.starter.spacingsAndBorders.spacing
import com.compose.starter.theme.mediaDetailFillColor
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.arrow_back
import composestarter.composeapp.generated.resources.fav_filled
import composestarter.composeapp.generated.resources.fav_unfilled
import composestarter.composeapp.generated.resources.featured_casts
import composestarter.composeapp.generated.resources.keywords
import composestarter.composeapp.generated.resources.overview_not_present
import composestarter.composeapp.generated.resources.recommendations
import composestarter.composeapp.generated.resources.reviews
import composestarter.composeapp.generated.resources.similar
import org.jetbrains.compose.resources.stringResource

@Composable
fun MovieDetailScreen(
    id: String,
    viewModel: MovieDetailScreenModel,
    navigate: (Movie) -> Unit,
    onBack: () -> Unit,
) {
    val uiState by viewModel.movieDetailUiState.collectAsStateWithLifecycle()
    var dominantColor by remember { mutableStateOf<Color?>(null) }

    LaunchedEffect(Unit) {
        viewModel.fetchInitialMovieDetailData(id)
    }

    Scaffold(
        modifier = statusNavModifier(statusBarColor = dominantColor)
    ) { padding ->
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
                        key = uiState.movieDetail.hashCode()
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

                    item {
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                    }

                    item(
                        key = uiState.movieDetail?.overview.hashCode(),
                    ) {
                        OverviewSection(
                            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                            title = uiState.movieDetail?.title,
                            overview = uiState.movieDetail?.overview ?: stringResource(Res.string.overview_not_present),
                            posterPath = uiState.movieDetail?.backdropPath,
                            importantCrewMap = uiState.importantCrewMap,
                            overviewPairs = uiState.overviewPairs,
                            externalLinks = uiState.externalLinks,
                        )
                    }

                    if (uiState.movieDetail?.videos.isNullOrEmpty().not() || uiState.movieDetail?.images.isNullOrEmpty().not() || uiState.movieDetail?.backdrops.isNullOrEmpty().not()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item(key = uiState.movieDetail?.videos.hashCode()) {
                            MediaSection(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                videos = uiState.movieDetail?.videos,
                                posters = uiState.movieDetail?.images,
                                backdrops = uiState.movieDetail?.backdrops,
                            ) {

                            }
                        }
                    }

                    if ((uiState.movieDetail?.casts ?: emptyList()).isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item {
                            DetailTitleWithIcon(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                title = stringResource(Res.string.featured_casts),
                                endIcon = Icons.Sharp.MoreVert,
                                onEndIconClick = {
                                    navigate(Movie.CastAndCrewList)
                                }
                            )
                        }

                        item {
                            FeaturedCastRow(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                casts = uiState.movieDetail?.casts ?: emptyList(),
                                navigateToDetail = navigate
                            )
                        }
                    }

                    if ((uiState.movieDetail?.reviews ?: emptyList()).isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item {
                            DetailTitleWithIcon(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                title = stringResource(Res.string.reviews),
                                endIcon = Icons.Sharp.MoreVert,
                            )
                        }

                        item(
                            key = uiState.movieDetail?.reviews.hashCode()
                        ) {
                            ReviewCard(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                review = uiState.movieDetail?.reviews?.first()!!
                            )
                        }
                    }

                    if ((uiState.movieDetail?.recommendations ?: emptyList()).isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item {
                            DetailTitleWithIcon(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                title = stringResource(Res.string.recommendations)
                            )
                        }

                        item {
                            RecommendedLazyRow(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                recommendations = uiState.movieDetail?.recommendations!!
                            )
                        }
                    }


                    if ((uiState.movieDetail?.similarList ?: emptyList()).isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item {
                            DetailTitleWithIcon(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                title = stringResource(Res.string.similar)
                            )
                        }

                        item {
                            SimilarLazyRow(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                similars = uiState.movieDetail?.similarList!!
                            )
                        }
                    }

                    if ((uiState.movieDetail?.keywords ?: emptyList()).isNotEmpty()) {
                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }

                        item {
                            DetailTitleWithIcon(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                title = stringResource(Res.string.keywords)
                            )
                        }

                        item {
                            KeywordsFlowRow(
                                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.default),
                                keywords = uiState.movieDetail?.keywords!!
                            )
                        }

                        item {
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.medium))
                        }
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