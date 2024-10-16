package com.compose.starter.di

import com.compose.starter.features.movieDetailScreen.MediaDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow

class ShareMediaData {
    val movieState = MutableStateFlow(MediaDetailUiState())

    fun updateMovieState(state: MediaDetailUiState) {
        movieState.value = state
    }
}