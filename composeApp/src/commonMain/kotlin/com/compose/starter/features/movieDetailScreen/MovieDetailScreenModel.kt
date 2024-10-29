package com.compose.starter.features.movieDetailScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailScreenModel(
    private val movieDetailUseCase: MovieDetailScreenUseCase,
    private val repository: MovieDetailScreenRepository,
) : ViewModel() {

    var movieDetailUiState = MutableStateFlow(MovieDetailUiState())
        private set

    fun fetchInitialMovieDetailData(movieId: String) {
        viewModelScope.launch {
            movieDetailUseCase(movieId).collectLatest { invokedDetailState ->
                movieDetailUiState.value = invokedDetailState
            }
        }
    }

    fun onEvent(event: MediaDetailUiEvent) {
        when (event) {
            is MediaDetailUiEvent.OnAddToWatchList -> addToWatchList(event)
            is MediaDetailUiEvent.OnFavorite -> addToFavorite(event)
            is MediaDetailUiEvent.OnRatingChanged -> addRating(event)
        }
    }

    private fun addToFavorite(event: MediaDetailUiEvent.OnFavorite) {
        viewModelScope.launch {
            repository.addToFavorite(
                mediaType = event.mediaType,
                mediaId = event.mediaId ?: "",
                sessionId = event.sessionId,
                accountId = event.accountId,
                isFavorite = event.isFavorite
            ).collectLatest {
                movieDetailUiState.update {
                    it.copy(isFavorite = event.isFavorite)
                }
            }
        }
    }

    private fun addToWatchList(event: MediaDetailUiEvent.OnAddToWatchList) {
        viewModelScope.launch {
            repository.addToWatchList(
                mediaType = event.mediaType,
                mediaId = event.mediaId ?: "",
                sessionId = event.sessionId,
                accountId = event.accountId,
                shouldAddToWatchList = event.shouldAdd
            ).collectLatest {
                movieDetailUiState.update {
                    it.copy(shouldAddToWatchList = event.shouldAdd)
                }
            }
        }
    }

    private fun addRating(event: MediaDetailUiEvent.OnRatingChanged) {
        viewModelScope.launch {
            repository.addRating(
                rating = event.rating,
                movieId = event.mediaId ?: "",
                sessionId = event.sessionId,
            ).collectLatest { isSuccess ->
                movieDetailUiState.update {
                    it.copy(rating = if (isSuccess) event.rating else it.rating)
                }
            }
        }
    }

    public override fun onCleared() {
        super.onCleared()
    }
}