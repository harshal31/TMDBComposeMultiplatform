package com.compose.starter.features.tvSeriesScreen

import androidx.compose.runtime.Stable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData

@Stable
data class TvSeriesScreenUiState(
    val errorCode: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedTvSeries: List<TmdbMediaData> = emptyList(),
    val weeklyTrendingTvSeries: List<TmdbMediaData> = emptyList(),
    val popularTvSeries: List<TmdbMediaData> = emptyList(),
    val freeToWatchTvSeries: List<TmdbMediaData> = emptyList(),
    val upcomingTvSeries: List<TmdbMediaData> = emptyList(),
    val airingToday: List<TmdbMediaData> = emptyList(),
)