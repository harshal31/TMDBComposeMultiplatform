package com.compose.starter.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class AccountDetail(
    @SerialName("avatar")
    val avatar: Avatar? = null,
    @SerialName("id")
    val id: Int? = null, // 9109399
    @SerialName("include_adult")
    val includeAdult: Boolean? = null, // false
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // IN
    @SerialName("iso_639_1")
    val iso6391: String? = null, // en
    @SerialName("name")
    val name: String? = null, // Harshal Chaudhari
    @SerialName("username")
    val username: String? = null, // harshal.chaudhari3131
)

@Serializable
data class Avatar(
    @SerialName("gravatar")
    val gravatar: Gravatar? = null,
    @SerialName("tmdb")
    val tmdb: Tmdb? = null,
)

@Serializable
data class Gravatar(
    @SerialName("hash")
    val hash: String? = null, // c021b2f79bfaa8d2f7a4b1544984db79
)

@Serializable
data class Tmdb(
    @SerialName("avatar_path")
    val avatarPath: String? = null, // null
)