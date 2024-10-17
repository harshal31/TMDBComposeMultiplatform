package com.compose.starter.features.peopleScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData

@Immutable
data class PeopleScreenUiState(
    val apiState: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedPeople: List<TmdbMediaData> = emptyList(),
    val weeklyTrendingPeople: List<TmdbMediaData> = emptyList(),
    val popularPeople: List<TmdbMediaData> = emptyList(),
)