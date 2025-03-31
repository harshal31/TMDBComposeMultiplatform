package com.compose.starter.features.personDetailScreen

import com.compose.starter.networking.ApiState
import com.compose.starter.networking.ApiState.Companion.mapErrorToApiIssue
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.utilities.immutableList
import com.compose.starter.utilities.immutableMap
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class PersonDetailUseCase(
    private val repository: PersonDetailRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(
        personId: Int,
        langCode: String = DefaultParameter.LANG_CODE,
    ): Flow<PersonDetailUiState> {
        return flow {
            emit(repository.fetchPersonDetails(personId.toString(), langCode))
        }.map { detail ->
            if (detail.isFailure) {
                PersonDetailUiState(
                    apiState = detail.exceptionOrNull()?.message.mapErrorToApiIssue()
                )
            } else {
                detail.getOrNull()?.mappedToPersonDetail(DefaultParameter.defaultLanguage())?.let {
                    PersonDetailUiState(
                        apiState = ApiState.NONE,
                        avatarPath = it.avatarPath,
                        personName = it.personName,
                        years = it.years,
                        bio = it.bio,
                        knownFor = it.knownFor,
                        bornOn = it.bornOn,
                        gender = it.gender?.toInt(),
                        birthPlace = it.birthPlace,
                        externalLinks = immutableList(it.externalLinks),
                        profileImages = immutableList(it.profileImages),
                        movieCredits = immutableMap(it.movieCredits),
                        tvCredits = immutableMap(it.tvCredits),
                    )
                } ?: PersonDetailUiState(
                    apiState = ApiState.ERROR
                )
            }
        }.flowOn(dispatcher)
    }
}