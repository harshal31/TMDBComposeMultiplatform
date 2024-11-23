package com.compose.starter.features.peopleScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.TmdbMediaData
import com.compose.starter.utilities.immutableList
import com.compose.starter.utilities.second
import com.compose.starter.utilities.third
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonScreenViewModel(private val repository: PersonScreenRepository) : ViewModel() {
    private var isApiCalled = false
    private val _uiState = MutableStateFlow(PersonScreenUiState())
    val uiState = _uiState.asStateFlow()


    fun fetchPeopleList() {
        if (isApiCalled) {
            return
        }
        _uiState.update {
            it.copy(
                apiState = ApiState.CIRCULAR_LOADING
            )
        }
        isApiCalled = true
        viewModelScope.launch {
            val dailyTrending = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.PERSON,
                    timeWindow = DefaultParameter.DAILY
                )
            }

            val weekly = async {
                repository.getTrendingMedia(
                    media = Parameter.Media.PERSON,
                    timeWindow = DefaultParameter.WEEKLY
                )
            }

            val popularMovies = async {
                repository.getPopularMedia(media = Endpoint.POPULAR to Parameter.Media.PERSON)
            }

            val result = awaitAll(
                dailyTrending,
                weekly,
                popularMovies,
            )

            if (result.all { it.isSuccess }) {
                _uiState.update { peoples ->
                    peoples.copy(
                        apiState = ApiState.NONE,
                        dailyTrendedPeople = immutableList(
                            result.first().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        weeklyTrendingPeople = immutableList(
                            result.second().getOrNull()?.results?.map {
                                TmdbMediaData(
                                    imageUrl = it.posterPath ?: it.profilePath ?: "",
                                    mediaId = it.id.toString()
                                )
                            }
                        ),
                        popularPeople = immutableList(
                            result.third().getOrNull()?.results?.map {
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
                it.copy(apiState = ApiState.ERROR)
            }
        }
    }
}