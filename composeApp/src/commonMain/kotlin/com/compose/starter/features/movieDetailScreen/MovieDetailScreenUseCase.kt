package com.compose.starter.features.movieDetailScreen

import com.compose.starter.di.ShareMediaData
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
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class MovieDetailScreenUseCase(
    private val movieDetailRepo: MovieDetailScreenRepository,
    private val settingRepo: SettingsScreenRepository,
    private val shareMediaData: ShareMediaData,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(
        movieId: String,
        langCode: String = DefaultParameter.LANG_CODE,
    ): Flow<MovieDetailUiState> {
        return combine(
            movieDetailRepo.getAllMovieDetails(movieId, langCode),
            movieDetailRepo.getSessionId(),
            settingRepo.fetchUserInformation(),
            movieDetailRepo.getImages(movieId, langCode.split("-").first()),
        ) { detail, session, accountId, images ->
            if (detail.isFailure) {
                MovieDetailUiState(
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


                movieDetail?.credits?.let { credit ->
                    val crewMap = credit.crew?.sortedBy {
                        it.department ?: ""
                    }?.groupBy {
                        it.department ?: ""
                    }?.mapValues {
                        it.value.sortedBy { c -> c.name }.map { c -> c.mapToMappedCrew() }
                    } ?: emptyMap()

                    shareMediaData.updateCastAndCrewData(
                        mediaName = movieDetail.title ?: "",
                        casts = credit.cast?.map { it.mapToMappedCast() } ?: emptyList(),
                        crewMap = crewMap,
                        crewSize = credit.crew?.size ?: 0
                    )
                }

                MovieDetailUiState(
                    apiState = ApiState.NONE,
                    movieDetail = movieDetail?.copy(images = images.getOrNull())
                        ?.mapToMappedMovieDetail(),
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
            movieDetailRepo.getAccountStates(movieId, detail.sessionId).map {
                val actState = it.getOrNull()
                detail.copy(
                    rating = actState?.getRating() ?: 0,
                    isFavorite = actState?.favorite ?: false,
                    shouldAddToWatchList = actState?.watchlist ?: false
                )
            }
        }.flowOn(dispatcher)
    }
}