package com.compose.starter.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FavoriteBody(
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("media_id")
    val mediaId: String? = null,
    @SerialName("favorite")
    val favorite: Boolean? = null,
)

@Serializable
data class WatchlistBody(
    @SerialName("media_type")
    val mediaType: String? = null,
    @SerialName("media_id")
    val mediaId: String? = null,
    @SerialName("watchlist")
    val shouldAddToWatchList: Boolean? = null,
)