package com.compose.starter.features.peopleScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.immutableList

@Immutable
data class PersonScreenUiState(
    val apiState: ApiState = ApiState.CIRCULAR_LOADING,
    val dailyTrendedPeople: ImmutableList<TmdbMediaData> = immutableList(),
    val weeklyTrendingPeople: ImmutableList<TmdbMediaData> = immutableList(),
    val popularPeople: ImmutableList<TmdbMediaData> = immutableList(),
)