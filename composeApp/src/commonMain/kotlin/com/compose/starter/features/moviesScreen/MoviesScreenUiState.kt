package com.compose.starter.features.moviesScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData

@Immutable
data class MoviesScreenUiState(
    val errorCode: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedMovies: List<TmdbMediaData> = emptyList(),
    val weeklyTrendingMovies: List<TmdbMediaData> = emptyList(),
    val popularMovies: List<TmdbMediaData> = emptyList(),
    val freeToWatchMovies: List<TmdbMediaData> = emptyList(),
    val upcomingMovies: List<TmdbMediaData> = emptyList(),
    val nowInTheatres: List<TmdbMediaData> = emptyList(),
)