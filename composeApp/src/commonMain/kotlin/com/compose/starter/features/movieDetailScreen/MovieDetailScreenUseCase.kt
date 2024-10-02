package com.compose.starter.features.movieDetailScreen

import com.compose.starter.features.settingsScreen.SettingsScreenRepository
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.ApiState.Companion.mapErrorToApiIssue
import com.compose.starter.networking.DefaultParameter
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
            repository.getImages(movieId, langCode),
        ) { detail, session, accountId, images ->
            if (detail.isFailure) {
                MediaDetailUiState(
                    apiState = detail.exceptionOrNull()?.message.mapErrorToApiIssue(),
                    sessionId = session,
                    accountId = accountId.getOrNull()?.id?.toString()
                )
            } else {
                MediaDetailUiState(
                    apiState = ApiState.NONE,
                    movieDetail = detail.getOrNull()?.copy(
                        images = images.getOrNull()
                    ),
                    sessionId = session,
                    accountId = accountId.getOrNull()?.id?.toString(),
                    importantCrewMap = detail.getOrNull()?.credits?.getImportantCastAndCrew()
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