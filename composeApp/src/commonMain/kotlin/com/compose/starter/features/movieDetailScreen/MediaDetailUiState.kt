package com.compose.starter.features.movieDetailScreen

import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaDetail
import org.jetbrains.compose.resources.StringResource

data class MediaDetailUiState(
    val apiState: ApiState = ApiState.HORIZONTAL_LOADING,
    val movieDetail: TmdbMediaDetail? = null,
    val certification: String? = null,
    val releaseYear: String? = null,
    val sessionId: String? = "",
    val accountId: String? = "",
    val isFavorite: Boolean = false,
    val shouldAddToWatchList: Boolean = false,
    val rating: Int = 0,
    val overviewPairs: List<List<OverviewPair>> = emptyList(),
    val importantCrewMap: List<Pair<String, String>> = emptyList(),
)


sealed interface MediaDetailUiEvent {
    data class OnFavorite(
        val mediaType: String,
        val isFavorite: Boolean,
        val mediaId: String?,
        val sessionId: String?,
        val accountId: String?,
    ) : MediaDetailUiEvent

    data class OnAddToWatchList(
        val mediaType: String,
        val shouldAdd: Boolean,
        val mediaId: String?,
        val sessionId: String?,
        val accountId: String?,
    ) : MediaDetailUiEvent

    data class OnRatingChanged(
        val rating: Int,
        val mediaId: String?,
        val sessionId: String?,
    ) : MediaDetailUiEvent
}

data class OverviewPair(
    val title: StringResource,
    val value: String?,
)