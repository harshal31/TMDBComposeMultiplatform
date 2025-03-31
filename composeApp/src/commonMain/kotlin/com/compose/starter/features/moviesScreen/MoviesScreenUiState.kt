package com.compose.starter.features.moviesScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.immutableList

@Immutable
data class MoviesScreenUiState(
    val errorCode: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedMovies: ImmutableList<TmdbMediaData> = immutableList(),
    val weeklyTrendingMovies: ImmutableList<TmdbMediaData> = immutableList(),
    val popularMovies: ImmutableList<TmdbMediaData> = immutableList(),
    val freeToWatchMovies: ImmutableList<TmdbMediaData> = immutableList(),
    val upcomingMovies: ImmutableList<TmdbMediaData> = immutableList(),
    val nowInTheatres: ImmutableList<TmdbMediaData> = immutableList(),
)