package com.compose.starter.features.personDetailScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.commonUi.ExternalLink
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.MappedMovieCredit
import com.compose.starter.networking.model.MappedTvCredit

@Immutable
data class PersonDetailUiState(
    val apiState: ApiState = ApiState.HORIZONTAL_LOADING,
    val personName: String? = null,
    val avatarPath: String? = null,
    val bornOn: String? = null,
    val years: Int? = null,
    val birthPlace: String? = null,
    val gender: Int? = null,
    val knownFor: String? = null,
    val bio: String? = null,
    val externalLinks: List<ExternalLink> = emptyList(),
    val profileImages: List<String> = emptyList(),
    val movieCredits: Map<String, List<MappedMovieCredit>> = emptyMap(),
    val tvCredits: Map<String, List<MappedTvCredit>> = emptyMap(),
)