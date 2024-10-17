package com.compose.starter.di

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.model.MappedCast
import com.compose.starter.networking.model.MappedCrew
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * This object is used to share data from Movies Screen to Movies Detail
 * and Tv Screen to Tv Detail Screen
 */
class ShareMediaData {
    val movieState = MutableStateFlow(MediaDetailUiState())

    fun updateCastAndCrewData(
        mediaName: String,
        casts: List<MappedCast>,
        crewMap: Map<String, List<MappedCrew>>,
        crewSize: Int,
    ) {
        movieState.update {
            it.copy(
                mediaName = mediaName,
                casts = casts,
                crews = crewMap,
                crewSize = crewSize
            )
        }
    }
}


@Immutable
data class MediaDetailUiState(
    val mediaName: String = "",
    val casts: List<MappedCast> = emptyList(),
    val crews: Map<String, List<MappedCrew>> = emptyMap(),
    val crewSize: Int = 0,
)