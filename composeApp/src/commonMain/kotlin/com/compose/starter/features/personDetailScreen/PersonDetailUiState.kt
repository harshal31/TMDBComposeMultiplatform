package com.compose.starter.features.personDetailScreen

import androidx.compose.runtime.Immutable
import com.compose.starter.commonUi.ExternalLink
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.model.MappedMovieCredit
import com.compose.starter.networking.model.MappedTvCredit
import com.compose.starter.utilities.ImmutableList
import com.compose.starter.utilities.ImmutableMap
import com.compose.starter.utilities.immutableList
import com.compose.starter.utilities.immutableMap

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
    val externalLinks: ImmutableList<ExternalLink> = immutableList(),
    val profileImages: ImmutableList<String> = immutableList(),
    val movieCredits: ImmutableMap<String, List<MappedMovieCredit>> = immutableMap(),
    val tvCredits: ImmutableMap<String, List<MappedTvCredit>> = immutableMap(),
)