package com.compose.starter.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class FavoriteWatchlistResult(
    @SerialName("status_code")
    val statusCode: Int? = null, // 1
    @SerialName("status_message")
    val statusMessage: String? = null, // Success.
)