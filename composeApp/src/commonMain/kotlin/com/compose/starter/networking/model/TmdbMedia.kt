package com.compose.starter.networking.model

import androidx.compose.runtime.Immutable
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TMDBMedia(
    @SerialName("page")
    val page: Int? = 0, // 1
    @SerialName("results")
    val results: List<TMDBMediaResult> = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int? = 0, // 1000
    @SerialName("total_results")
    val totalResults: Int? = 0, // 20000
)

@Serializable
data class TMDBMediaResult(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /p5kpFS0P3lIwzwzHBOULQovNWyj.jpg
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null, // 1032823
    @SerialName("media_type")
    val mediaType: String? = null, // movie
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // Trap
    @SerialName("overview")
    val overview: String? = null, // A father and teen daughter attend a pop concert, where they realize they're at the center of a dark and sinister event.
    @SerialName("popularity")
    val popularity: Double? = null, // 557.533
    @SerialName("poster_path")
    val posterPath: String? = null, // /jwoaKYVqPgYemFpaANL941EF94R.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 2024-07-31
    @SerialName("title")
    val title: String? = null, // Trap
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 6.458
    @SerialName("vote_count")
    val voteCount: Int? = null, // 628
    @SerialName("known_for")
    val knownFor: List<KnownFor> = emptyList(),
    @SerialName("known_for_department")
    val knownForDepartment: String? = null,
    @SerialName("original_name")
    val originalName: String? = null, // Gary Oldman
    @SerialName("profile_path")
    val profilePath: String? = null, // /2v9FVVBUrrkW2m3QOcYkuhq9A6o.jpg
    @SerialName("gender")
    val gender: Int? = null, // 2
)


@Serializable
data class KnownFor(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /dqK9Hag1054tghRQSqLSfrkvQnA.jpg
    @SerialName("first_air_date")
    val firstAirDate: String? = null, // 2007-02-15
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null, // 155
    @SerialName("media_type")
    val mediaType: String? = null, // movie
    @SerialName("name")
    val name: String? = null, // Naruto Shippūden
    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_name")
    val originalName: String? = null, // ナルト 疾風伝
    @SerialName("original_title")
    val originalTitle: String? = null, // The Dark Knight
    @SerialName("overview")
    val overview: String? = null, // Batman raises the stakes in his war on crime. With the help of Lt. Jim Gordon and District Attorney Harvey Dent, Batman sets out to dismantle the remaining criminal organizations that plague the streets. The partnership proves to be effective, but they soon find themselves prey to a reign of chaos unleashed by a rising criminal mastermind known to the terrified citizens of Gotham as the Joker.
    @SerialName("popularity")
    val popularity: Double? = null, // 166.733
    @SerialName("poster_path")
    val posterPath: String? = null, // /qJ2tW6WMUDux911r6m7haRef0WH.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 2008-07-16
    @SerialName("title")
    val title: String? = null, // The Dark Knight
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 8.516
    @SerialName("vote_count")
    val voteCount: Int? = null, // 32495
)

@Serializable
@Immutable
data class TmdbMediaData(
    val imageUrl: String?,
    val mediaId: String?,
)