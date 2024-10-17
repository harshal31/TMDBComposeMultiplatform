package com.compose.starter.networking.model

import androidx.compose.runtime.Immutable
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.utilities.formatDate
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class TmdbMediaDetail(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("alternative_titles")
    val alternativeTitles: AlternativeTitles? = null,
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /dTaqzXA1auKRUjHPQjR3u0W7Gfl.jpg
    @SerialName("belongs_to_collection")
    val belongsToCollection: BelongToCollection? = null, // null
    @SerialName("budget")
    val budget: Int? = null, // 20000000
    @SerialName("credits")
    val credits: Credits? = null,
    @SerialName("external_ids")
    val externalIds: ExternalIds? = null,
    @SerialName("genres")
    val genres: List<Genre>? = emptyList(),
    @SerialName("homepage")
    val homepage: String? = null, // https://mgm.com/movies/blink-twice
    @SerialName("id")
    val id: Int? = null, // 840705
    @SerialName("images")
    val images: Images? = null,
    @SerialName("imdb_id")
    val imdbId: String? = null, // tt14858658
    @SerialName("keywords")
    val keywords: Keywords? = null,
    @SerialName("origin_country")
    val originCountry: List<String>? = emptyList(),
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // Blink Twice
    @SerialName("overview")
    val overview: String? = null, // When tech billionaire Slater King meets cocktail waitress Frida at his fundraising gala, he invites her to join him and his friends on a dream vacation on his private island. But despite the epic setting, beautiful people, ever-flowing champagne, and late-night dance parties, Frida can sense that there’s something sinister hiding beneath the island’s lush façade.
    @SerialName("popularity")
    val popularity: Double? = null, // 451.122
    @SerialName("poster_path")
    val posterPath: String? = null, // /lZGOK0I2DJSRlEPNOAFTSNxSjDD.jpg
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompany>? = emptyList(),
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountry>? = emptyList(),
    @SerialName("recommendations")
    val recommendations: Recommendations? = null,
    @SerialName("release_date")
    val releaseDate: String? = null, // 2024-08-21
    @SerialName("release_dates")
    val releaseDates: ReleaseDates? = null,
    @SerialName("revenue")
    val revenue: Int? = null, // 43136879
    @SerialName("reviews")
    val reviews: Reviews? = null,
    @SerialName("runtime")
    val runtime: Int? = null, // 103
    @SerialName("similar")
    val similar: Similar? = null,
    @SerialName("spoken_languages")
    val spokenLanguages: List<SpokenLanguage>? = emptyList(),
    @SerialName("status")
    val status: String? = null, // Released
    @SerialName("tagline")
    val tagline: String? = null, // Are you having a good time?
    @SerialName("title")
    val title: String? = null, // Blink Twice
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("videos")
    val videos: Videos? = null,
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 6.665
    @SerialName("vote_count")
    val voteCount: Int? = null, // 294
) {

    fun mapToMappedMovieDetail(): MappedMovieDetail {
        return MappedMovieDetail(
            title = title ?: "-",
            overview = overview ?: "",
            backdropPath = backdropPath ?: "-",
            posterPath = posterPath ?: "",
            genres = genres ?: emptyList(),
            id = id,
            voteAverage = voteAverage ?: 0.0,
            casts = credits?.cast?.map { it.mapToMappedCast() } ?: emptyList(),
            recommendations = recommendations?.results?.map { it.mapToMappedRecommended() }
                ?: emptyList(),
            similarList = similar?.results?.map { it.mapToMappedSimilar() } ?: emptyList(),
            videos = videos?.results?.map { it.mapToMappedVideo() } ?: emptyList(),
            images = images?.posters?.map { it.mapToMappedImages() } ?: emptyList(),
            backdrops = images?.backdrops?.map { it.mapToMappedBackdrops() } ?: emptyList(),
            reviews = reviews?.results?.map { it.mapToMappedReview() } ?: emptyList(),
            keywords = keywords?.keywords?.map { it.mapToMappedKeyword() } ?: emptyList(),
        )
    }
}

@Serializable
data class BelongToCollection(
    @SerialName("id")
    val id: Int? = null, // 294
    @SerialName("name")
    val voteCount: String? = null, // "Beetle"
)

@Serializable
data class AlternativeTitles(
    @SerialName("titles")
    val titles: List<Title>? = emptyList(),
)


@Serializable
data class Credits(
    @SerialName("cast")
    val cast: List<Cast>? = emptyList(),
    @SerialName("crew")
    val crew: List<Crew>? = emptyList(),
) {
    private val relevantJobs = listOf(
        "Director", "Screenplay", "Story", "Characters", "Producer", "Writer", "Novel"
    )

    fun getImportantCastAndCrew(): List<Pair<String, String>> {
        return crew?.filter { it.job in relevantJobs }
            ?.sortedWith(compareBy<Crew> { it.job != "Director" }.thenBy { it.job })
            ?.groupBy { (it.name ?: "") }
            ?.mapValues { it.value.map { c -> (c.job ?: "") }.toHashSet().joinToString() }
            ?.map { it.key to it.value } ?: emptyList()
    }
}

@Serializable
data class ExternalIds(
    @SerialName("facebook_id")
    val facebookId: String? = null, // blinktwicemovie
    @SerialName("imdb_id")
    val imdbId: String? = null, // tt14858658
    @SerialName("instagram_id")
    val instagramId: String? = null, // blinktwicemovie
    @SerialName("twitter_id")
    val twitterId: String? = null, // blinktwicemovie
    @SerialName("wikidata_id")
    val wikidataId: String? = null, // Q113012523
)


@Immutable
@Serializable
data class Genre(
    @SerialName("id")
    val id: Int? = null, // 9648
    @SerialName("name")
    val name: String? = null, // Mystery
)

@Serializable
data class Keywords(
    @SerialName("keywords")
    val keywords: List<Keyword>? = emptyList(),
)


@Serializable
data class ProductionCompany(
    @SerialName("id")
    val id: Int? = null, // 21
    @SerialName("logo_path")
    val logoPath: String? = null, // /usUnaYV6hQnlVAXP6r4HwrlLFPG.png
    @SerialName("name")
    val name: String? = null, // Metro-Goldwyn-Mayer
    @SerialName("origin_country")
    val originCountry: String? = null, // US
)

@Serializable
data class ProductionCountry(
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // US
    @SerialName("name")
    val name: String? = null, // United States of America
)

@Serializable
data class Recommendations(
    @SerialName("page")
    val page: Int? = null, // 1
    @SerialName("results")
    val results: List<RecommendationList>? = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int? = null, // 2
    @SerialName("total_results")
    val totalResults: Int? = null, // 40
)

@Serializable
data class ReleaseDates(
    @SerialName("results")
    val results: List<ReleaseDateList>? = emptyList(),
) {

    fun getReleaseInfo(isoCode: String = DefaultParameter.REGION): Triple<String?, String?, String?> {
        val theatrical = 3
        val physical = 5
        val premiere = 1
        val releaseDate = results
            ?.firstOrNull { it.iso31661 == isoCode }?.releaseDates
            ?.filter { it.certification.isNullOrEmpty().not() }
            ?.firstOrNull { it.type in theatrical..physical || it.type == premiere }

        val releaseDateStr =
            if (releaseDate?.releaseDate.isNullOrEmpty()) null else releaseDate?.releaseDate
        val certification =
            if (releaseDate?.certification.isNullOrEmpty()) null else releaseDate?.certification
        val releaseYear = releaseDateStr?.split("-")?.firstOrNull()

        return Triple(certification, releaseDateStr, releaseYear)
    }
}

@Serializable
data class Reviews(
    @SerialName("page")
    val page: Int? = null, // 1
    @SerialName("results")
    val results: List<ReviewResult>? = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int? = null, // 1
    @SerialName("total_results")
    val totalResults: Int? = null, // 5
)

@Serializable
data class Similar(
    @SerialName("page")
    val page: Int? = null, // 1
    @SerialName("results")
    val results: List<SimilarResult>? = emptyList(),
    @SerialName("total_pages")
    val totalPages: Int? = null, // 3854
    @SerialName("total_results")
    val totalResults: Int? = null, // 77062
)

@Serializable
data class SpokenLanguage(
    @SerialName("english_name")
    val englishName: String? = null, // English
    @SerialName("iso_639_1")
    val iso6391: String? = null, // en
    @SerialName("name")
    val name: String? = null, // English
)

@Serializable
data class Videos(
    @SerialName("results")
    val results: List<ResultVideos>? = emptyList(),
)

@Serializable
data class Title(
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // US
    @SerialName("title")
    val title: String? = null, // Pussy Island
    @SerialName("type")
    val type: String? = null, // working title
)

@Serializable
data class Cast(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("cast_id")
    val castId: Int? = null, // 4
    @SerialName("character")
    val character: String? = null, // Frida
    @SerialName("credit_id")
    val creditId: String? = null, // 60d621f99512e1008063e8ee
    @SerialName("gender")
    val gender: Int? = null, // 1
    @SerialName("id")
    val id: Int? = null, // 1537686
    @SerialName("known_for_department")
    val knownForDepartment: String? = null, // Acting
    @SerialName("name")
    val name: String? = null, // Naomi Ackie
    @SerialName("order")
    val order: Int? = null, // 0
    @SerialName("original_name")
    val originalName: String? = null, // Naomi Ackie
    @SerialName("popularity")
    val popularity: Double? = null, // 39.337
    @SerialName("profile_path")
    val profilePath: String? = null, // /1MhRVn6xlShHhftRdK24HuO3TNR.jpg
) {
    fun mapToMappedCast(): MappedCast {
        return MappedCast(
            id = this.id,
            name = this.name ?: "-",
            profilePath = this.profilePath ?: "-",
            character = this.character ?: "-"
        )
    }
}

@Serializable
data class Crew(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("credit_id")
    val creditId: String? = null, // 620b9dacf03174006d62d615
    @SerialName("department")
    val department: String? = null, // Writing
    @SerialName("gender")
    val gender: Int? = null, // 2
    @SerialName("id")
    val id: Int? = null, // 2631137
    @SerialName("job")
    val job: String? = null, // Writer
    @SerialName("known_for_department")
    val knownForDepartment: String? = null, // Writing
    @SerialName("name")
    val name: String? = null, // E.T. Feigenbaum
    @SerialName("original_name")
    val originalName: String? = null, // E.T. Feigenbaum
    @SerialName("popularity")
    val popularity: Double? = null, // 2.901
    @SerialName("profile_path")
    val profilePath: String? = null, // /v40byHjEBXfuBj2OuqvM5YGPF6E.jpg
) {
    fun mapToMappedCrew(): MappedCrew {
        return MappedCrew(
            id = this.id,
            name = this.name ?: "-",
            profilePath = this.profilePath ?: "-",
            job = this.job ?: "-"
        )
    }
}

@Serializable
data class Keyword(
    @SerialName("id")
    val id: Int? = null, // 570
    @SerialName("name")
    val name: String? = null, // rape
) {
    fun mapToMappedKeyword(): MappedKeyword {
        return MappedKeyword(
            id = id,
            name = name ?: "-"
        )
    }
}

@Serializable
data class RecommendationList(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /oBmwaZ3NVa3JyZnv1qCo0znxjUw.jpg
    @SerialName("genre_ids")
    val genreIds: List<Int>? = emptyList(),
    @SerialName("id")
    val id: Int? = null, // 1029281
    @SerialName("media_type")
    val mediaType: String? = null, // movie
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // Strange Darling
    @SerialName("overview")
    val overview: String? = null, // Nothing is what it seems when a twisted one-night stand spirals into a serial killer’s vicious murder spree.
    @SerialName("popularity")
    val popularity: Double? = null, // 37.007
    @SerialName("poster_path")
    val posterPath: String? = null, // /nnfATg1rmkRtjQJpQ9rm5iV13N0.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 2024-08-22
    @SerialName("title")
    val title: String? = null, // Strange Darling
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 7.8
    @SerialName("vote_count")
    val voteCount: Int? = null, // 20
) {
    fun mapToMappedRecommended(): MappedRecommended {
        return MappedRecommended(
            id = id,
            posterPath = posterPath ?: ""
        )
    }
}

@Serializable
data class ReleaseDateList(
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // AR
    @SerialName("release_dates")
    val releaseDates: List<ReleaseDate>? = emptyList(),
)

@Serializable
data class ReleaseDate(
    @SerialName("certification")
    val certification: String? = null,
    @SerialName("descriptors")
    val descriptors: List<String>? = emptyList(),
    @SerialName("iso_639_1")
    val iso6391: String? = null,
    @SerialName("note")
    val note: String? = null,
    @SerialName("release_date")
    val releaseDate: String? = null, // 2024-08-21T00:00:00.000Z
    @SerialName("type")
    val type: Int? = null, // 3
)

@Serializable
data class ReviewResult(
    @SerialName("author")
    val author: String? = null, // Manuel São Bento
    @SerialName("author_details")
    val authorDetails: AuthorDetails? = null,
    @SerialName("content")
    val content: String? = null, // FULL SPOILER-FREE REVIEW @ https://fandomwire.com/blink-twice-review-zoe-kravitzs-directorial-debut-delivers-a-thought-provoking-exploration-of-power-and-trauma/"Blink Twice marks a promising debut for Zoë Kravitz as a filmmaker, demonstrating that her personal creativity and vision aren't confined to the side of the camera she works on.Despite some shortcomings in tone management, the compelling narrative, supported by memorable performances from Ackie, Tatum, and Arjona, and an immersive, unsettling atmosphere, make this psychological thriller an engaging, cathartic experience. Kravitz tackles relevant, complex contemporary themes, offering a thought-provoking reflection on power, trauma, forgiveness, and forgetting.Above all, this debut will leave many viewers eager to see what Kravitz brings to the world of cinema in the near future."Rating: B+
    @SerialName("created_at")
    val createdAt: String? = null, // 2024-08-24T13:10:44.123Z
    @SerialName("id")
    val id: String? = null, // 66c9dbd435f89cb980eab341
    @SerialName("updated_at")
    val updatedAt: String? = null, // 2024-08-24T13:10:44.284Z
    @SerialName("url")
    val url: String? = null, // https://www.themoviedb.org/review/66c9dbd435f89cb980eab341
) {
    fun mapToMappedReview(): MappedReview {
        return MappedReview(
            id = id,
            authorName = authorDetails?.name,
            authorUsername = authorDetails?.username ?: "-",
            avatarPath = authorDetails?.avatarPath ?: "-",
            content = content ?: "-",
            createdAt = createdAt.formatDate() ?: "-",
            reviewUrl = url ?: "-"
        )
    }
}

@Serializable
data class AuthorDetails(
    @SerialName("avatar_path")
    val avatarPath: String? = null, // /yz2HPme8NPLne0mM8tBnZ5ZWJzf.jpg
    @SerialName("name")
    val name: String? = null, // Manuel São Bento
    @SerialName("rating")
    val rating: Double? = null, // 7
    @SerialName("username")
    val username: String? = null, // msbreviews
)

@Serializable
data class SimilarResult(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /mmd1HnuvAzFc4iuVJcnBrhDNEKr.jpg
    @SerialName("genre_ids")
    val genreIds: List<Int>? = emptyList(),
    @SerialName("id")
    val id: Int? = null, // 694
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // The Shining
    @SerialName("overview")
    val overview: String? = null, // Jack Torrance accepts a caretaker job at the Overlook Hotel, where he, along with his wife Wendy and their son Danny, must live isolated from the rest of the world for the winter. But they aren't prepared for the madness that lurks within.
    @SerialName("popularity")
    val popularity: Double? = null, // 57.201
    @SerialName("poster_path")
    val posterPath: String? = null, // /xazWoLealQwEgqZ89MLZklLZD3k.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 1980-05-23
    @SerialName("title")
    val title: String? = null, // The Shining
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 8.215
    @SerialName("vote_count")
    val voteCount: Int? = null, // 17253
) {
    fun mapToMappedSimilar(): MappedSimilar {
        return MappedSimilar(
            id = id,
            posterPath = posterPath ?: ""
        )
    }
}

@Serializable
data class ResultVideos(
    @SerialName("id")
    val id: String? = null, // 66dbfedfd1b6aaf74d6d0896
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // US
    @SerialName("iso_639_1")
    val iso6391: String? = null, // en
    @SerialName("key")
    val key: String? = null, // 3q1NJCaRKZc
    @SerialName("name")
    val name: String? = null, // Power & Pleasure with Zöe Kravitz
    @SerialName("official")
    val official: Boolean? = null, // true
    @SerialName("published_at")
    val publishedAt: String? = null, // 2024-09-06T18:03:37.000Z
    @SerialName("site")
    val site: String? = null, // YouTube
    @SerialName("size")
    val size: Int? = null, // 1080
    @SerialName("type")
    val type: String? = null, // Featurette
) {
    fun mapToMappedVideo(): MappedVideo {
        return MappedVideo(
            key = key ?: "-",
            name = name ?: "-",
            publishedAt = publishedAt.formatDate()
        )
    }
}


@Serializable
data class Images(
    @SerialName("backdrops")
    val backdrops: List<Backdrop>? = emptyList(),
    @SerialName("id")
    val id: Int? = null, // 550
    @SerialName("logos")
    val logos: List<Logo>? = emptyList(),
    @SerialName("posters")
    val posters: List<ImagePoster>? = emptyList(),
)

@Serializable
data class Backdrop(
    @SerialName("aspect_ratio")
    val aspectRatio: Double? = null, // 1.778
    @SerialName("file_path")
    val filePath: String? = null, // /hZkgoQYus5vegHoetLkCJzb17zJ.jpg
    @SerialName("height")
    val height: Int? = null, // 800
    @SerialName("iso_639_1")
    val iso6391: String? = null, // en
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 5.622
    @SerialName("vote_count")
    val voteCount: Int? = null, // 20
    @SerialName("width")
    val width: Int? = null, // 1422
) {
    fun mapToMappedBackdrops(): MappedBackdrops {
        return MappedBackdrops(filePath = filePath ?: "-")
    }
}

@Serializable
data class Logo(
    @SerialName("aspect_ratio")
    val aspectRatio: Double? = null, // 5.203
    @SerialName("file_path")
    val filePath: String? = null, // /c1KLulrIhUqY5fT42nmC5aERGCp.png
    @SerialName("height")
    val height: Int? = null, // 79
    @SerialName("iso_639_1")
    val iso6391: String? = null, // he
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 5.312
    @SerialName("vote_count")
    val voteCount: Int? = null, // 1
    @SerialName("width")
    val width: Int? = null, // 411
)

@Serializable
data class ImagePoster(
    @SerialName("aspect_ratio")
    val aspectRatio: Double? = null, // 0.667
    @SerialName("file_path")
    val filePath: String? = null, // /r3pPehX4ik8NLYPpbDRAh0YRtMb.jpg
    @SerialName("height")
    val height: Int? = null, // 900
    @SerialName("iso_639_1")
    val iso6391: String? = null, // pt
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 5.258
    @SerialName("vote_count")
    val voteCount: Int? = null, // 6
    @SerialName("width")
    val width: Int? = null, // 600
) {
    fun mapToMappedImages(): MappedImagePoster {
        return MappedImagePoster(filePath = filePath ?: "-")
    }
}


@Serializable
data class AccountState(
    @SerialName("favorite")
    val favorite: Boolean? = null,
    @SerialName("id")
    val id: Int? = null,
    @SerialName("rated")
    @Serializable(with = RatedDynamicSerializer::class)
    val rated: Any? = null,
    @SerialName("watchlist")
    val watchlist: Boolean? = null,
) {
    fun getRating(): Int {
        return when (rated) {
            is Rated -> rated.value?.div(2)?.toInt() ?: 0
            else -> 0
        }
    }
}

@Serializable
data class Rated(
    @SerialName("value")
    val value: Double? = null,
)

@Immutable
@Serializable
data class MappedCast(
    val id: Int? = null,
    val name: String = "",
    val profilePath: String = "",
    val character: String = "",
)


@Immutable
@Serializable
data class MappedCrew(
    val id: Int? = null,
    val name: String = "",
    val profilePath: String = "",
    val job: String = "",
)

@Immutable
@Serializable
data class MappedMovieDetail(
    val title: String = "",
    val overview: String = "",
    val backdropPath: String = "",
    val posterPath: String = "",
    val genres: List<Genre> = emptyList(),
    val id: Int? = null,
    val voteAverage: Double = 0.0,
    val casts: List<MappedCast> = emptyList(),
    val recommendations: List<MappedRecommended> = emptyList(),
    val similarList: List<MappedSimilar> = emptyList(),
    val videos: List<MappedVideo> = emptyList(),
    val images: List<MappedImagePoster> = emptyList(),
    val backdrops: List<MappedBackdrops> = emptyList(),
    val reviews: List<MappedReview> = emptyList(),
    val keywords: List<MappedKeyword> = emptyList(),
)

@Immutable
@Serializable
data class MappedVideo(
    val key: String,
    val name: String,
    val publishedAt: String,
)

@Immutable
@Serializable
data class MappedImagePoster(
    val filePath: String,
)

@Immutable
@Serializable
data class MappedBackdrops(
    val filePath: String,
)

@Immutable
@Serializable
data class MappedReview(
    val id: String? = null,
    val authorName: String? = null,
    val authorUsername: String = "",
    val avatarPath: String = "",
    val content: String = "",
    val createdAt: String = "",
    val reviewUrl: String = "",
)

@Immutable
@Serializable
data class MappedRecommended(
    val id: Int? = null,
    val posterPath: String = "",
)

@Immutable
@Serializable
data class MappedSimilar(
    val id: Int? = null,
    val posterPath: String = "",
)

@Immutable
@Serializable
data class MappedKeyword(
    val id: Int? = null,
    val name: String = "",
)