package com.compose.starter.features.moviesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.fifth
import com.compose.starter.utilities.fourth
import com.compose.starter.utilities.second
import com.compose.starter.utilities.sixth
import com.compose.starter.utilities.third
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesScreenViewModel(private val repository: MoviesScreenRepository) : ViewModel() {

    private var isApiAlreadyCalled = false
    private val _uiState = MutableStateFlow(MoviesScreenUiState())
    val uiState: StateFlow<MoviesScreenUiState> = _uiState

    fun fetchMoviesScreenData() {
        if (isApiAlreadyCalled) {
            return
        }
        _uiState.update {
            it.copy(errorCode = ApiState.CIRCULAR_LOADING)
        }
        isApiAlreadyCalled = true
        viewModelScope.launch {
            val dailyTrending = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.MOVIE,
                    timeWindow = DefaultParameter.DAILY
                )
            }

            val weekly = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.MOVIE,
                    timeWindow = DefaultParameter.WEEKLY
                )
            }

            val popularMovies = async {
                repository.getPopularMedia(media = Endpoint.POPULAR to Parameter.Media.MOVIE)
            }

            val upcomingMovies = async {
                repository.getPopularMedia(media = Endpoint.UPCOMING to Parameter.Media.MOVIE)
            }

            val nowInTheatres = async {
                repository.getPopularMedia(media = Endpoint.NOW_IN_THEATRES to Parameter.Media.MOVIE)
            }

            val freeToWatchMovies = async {
                repository.freeToWatchMovies(media = Parameter.Media.MOVIE)
            }

            val result = awaitAll(
                dailyTrending,
                weekly,
                popularMovies,
                upcomingMovies,
                nowInTheatres,
                freeToWatchMovies,
            )

            if (result.all { it.isSuccess }) {
                _uiState.update { movies ->
                    movies.copy(
                        errorCode = ApiState.NONE,
                        dailyTrendedMovies = result.first().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                        weeklyTrendingMovies = result.second().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                        popularMovies = result.third().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                        upcomingMovies = result.fourth().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                        nowInTheatres = result.fifth().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                        freeToWatchMovies = result.sixth().getOrNull()?.results?.map {
                            TmdbMediaData(
                                imageUrl = it.posterPath ?: it.profilePath ?: "",
                                mediaId = it.id.toString()
                            )
                        } ?: emptyList(),
                    )
                }
                return@launch
            }

            _uiState.update {
                it.copy(errorCode = ApiState.ERROR)
            }
        }
    }
}

