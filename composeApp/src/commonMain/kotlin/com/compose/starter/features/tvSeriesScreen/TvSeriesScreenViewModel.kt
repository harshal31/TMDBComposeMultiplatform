package com.compose.starter.features.tvSeriesScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.fifth
import com.compose.starter.utilities.fourth
import com.compose.starter.utilities.immutableList
import com.compose.starter.utilities.second
import com.compose.starter.utilities.sixth
import com.compose.starter.utilities.third
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TvSeriesScreenViewModel(private val repository: TvSeriesScreenRepository) : ViewModel() {

    private var isApiCalled = false
    private val _uiState = MutableStateFlow(TvSeriesScreenUiState())
    val uiState: StateFlow<TvSeriesScreenUiState> = _uiState


    fun fetchTvSeriesScreenData() {
        if (isApiCalled) {
            return
        }
        _uiState.update {
            it.copy(errorCode = ApiState.CIRCULAR_LOADING)
        }
        isApiCalled = true
        viewModelScope.launch {
            val dailyTrending = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.TV,
                    timeWindow = DefaultParameter.DAILY
                )
            }

            val weekly = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.TV,
                    timeWindow = DefaultParameter.WEEKLY
                )
            }

            val popularTvSeries = async {
                repository.getPopularMedia(media = Endpoint.POPULAR to Parameter.Media.TV)
            }

            val upcomingTvSeries = async {
                repository.getPopularMedia(media = Endpoint.TV_UPCOMING to Parameter.Media.TV)
            }

            val airingToday = async {
                repository.getPopularMedia(media = Endpoint.AIRING_TODAY to Parameter.Media.TV)
            }

            val freeToWatchTvSeries = async {
                repository.freeToWatchTvSeries(media = Parameter.Media.TV)
            }

            val result = awaitAll(
                dailyTrending,
                weekly,
                popularTvSeries,
                upcomingTvSeries,
                airingToday,
                freeToWatchTvSeries,
            )

            if (result.all { it.isSuccess }) {
                _uiState.update { series ->
                    series.copy(
                        errorCode = ApiState.NONE,
                        dailyTrendedTvSeries = immutableList(
                            result.first().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        weeklyTrendingTvSeries = immutableList(
                            result.second().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        popularTvSeries = immutableList(
                            result.third().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        upcomingTvSeries = immutableList(
                            result.fourth().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        airingToday = immutableList(
                            result.fifth().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        freeToWatchTvSeries = immutableList(
                            result.sixth().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
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