package com.compose.starter.features.movieDetailScreen

import com.compose.starter.features.settingsScreen.SettingsScreenRepository
import com.compose.starter.formatCurrency
import com.compose.starter.getDisplayLanguage
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.ApiState.Companion.mapErrorToApiIssue
import com.compose.starter.networking.DefaultParameter
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.budget
import composestarter.composeapp.generated.resources.original_language
import composestarter.composeapp.generated.resources.revenue
import composestarter.composeapp.generated.resources.status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map

class MovieDetailScreenUseCase(
    private val repository: MovieDetailScreenRepository,
    private val settingRepo: SettingsScreenRepository,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        movieId: String,
        langCode: String = DefaultParameter.LANG_CODE,
    ): Flow<MediaDetailUiState> {
        return combine(
            repository.getAllMovieDetails(movieId, langCode),
            repository.getSessionId(),
            settingRepo.fetchUserInformation(),
            repository.getImages(movieId, langCode.split("-").first()),
        ) { detail, session, accountId, images ->
            if (detail.isFailure) {
                MediaDetailUiState(
                    apiState = detail.exceptionOrNull()?.message.mapErrorToApiIssue(),
                    sessionId = session,
                    accountId = accountId.getOrNull()?.id?.toString()
                )
            } else {
                val movieDetail = detail.getOrNull()
                val releaseInfo = movieDetail?.releaseDates?.getReleaseInfo()
                val overviewPairs = listOf(
                    OverviewPair(
                        Res.string.status,
                        movieDetail?.status,
                    ),
                    OverviewPair(
                        Res.string.original_language,
                        getDisplayLanguage(movieDetail?.originalLanguage),
                    ),

                    OverviewPair(
                        Res.string.budget,
                        formatCurrency(movieDetail?.budget, langCode),
                    ),

                    OverviewPair(
                        Res.string.revenue,
                        formatCurrency(movieDetail?.revenue, langCode),
                    ),
                ).chunked(2)
                MediaDetailUiState(
                    apiState = ApiState.NONE,
                    movieDetail = movieDetail?.copy(images = images.getOrNull()),
                    sessionId = session,
                    certification = releaseInfo?.first,
                    releaseYear = releaseInfo?.third,
                    accountId = accountId.getOrNull()?.id?.toString(),
                    overviewPairs = overviewPairs,
                    importantCrewMap = movieDetail?.credits?.getImportantCastAndCrew()
                        ?: emptyList()
                )
            }
        }.flatMapLatest { detail ->
            repository.getAccountStates(movieId, detail.sessionId).map {
                val actState = it.getOrNull()
                detail.copy(
                    rating = actState?.getRating() ?: 0,
                    isFavorite = actState?.favorite ?: false,
                    shouldAddToWatchList = actState?.watchlist ?: false
                )
            }
        }
    }
}