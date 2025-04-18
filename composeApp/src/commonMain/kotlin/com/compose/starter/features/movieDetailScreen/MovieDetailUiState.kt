package com.compose.starter.features.movieDetailScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.commonUi.ExternalLink
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.MappedMovieDetail
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.immutableList
import org.jetbrains.compose.resources.StringResource

@Immutable
data class MovieDetailUiState(
    val apiState: ApiState = ApiState.HORIZONTAL_LOADING,
    val movieDetail: MappedMovieDetail? = null,
    val certification: String? = null,
    val releaseYear: String? = null,
    val sessionId: String? = "",
    val accountId: String? = "",
    val isFavorite: Boolean = false,
    val shouldAddToWatchList: Boolean = false,
    val rating: Int = 0,
    val overviewPairs: ImmutableList<List<OverviewPair>> = immutableList(),
    val importantCrewMap: ImmutableList<Pair<String, String>> = immutableList(),
    val externalLinks: ImmutableList<ExternalLink> = immutableList(),
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

@Immutable
data class OverviewPair(
    val title: StringResource,
    val value: String?,
)