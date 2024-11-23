package com.compose.starter.features.tvSeriesScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.immutableList

@Immutable
data class TvSeriesScreenUiState(
    val errorCode: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedTvSeries: ImmutableList<TmdbMediaData> = immutableList(),
    val weeklyTrendingTvSeries: ImmutableList<TmdbMediaData> = immutableList(),
    val popularTvSeries: ImmutableList<TmdbMediaData> = immutableList(),
    val freeToWatchTvSeries: ImmutableList<TmdbMediaData> = immutableList(),
    val upcomingTvSeries: ImmutableList<TmdbMediaData> = immutableList(),
    val airingToday: ImmutableList<TmdbMediaData> = immutableList(),
)