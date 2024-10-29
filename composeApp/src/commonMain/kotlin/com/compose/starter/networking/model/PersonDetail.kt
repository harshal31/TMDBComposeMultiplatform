package com.compose.starter.networking.model

import androidx.compose.runtime.Immutable
import com.compose.starter.commonUi.ExternalLink
import com.compose.starter.utilities.DateTimeManager
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.birthPlace
import composestarter.composeapp.generated.resources.bornOn
import composestarter.composeapp.generated.resources.gender
import composestarter.composeapp.generated.resources.knownFor
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.yearsUntil
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.StringResource

@Serializable
data class PersonDetail(
    @SerialName("adult")
    val adult: Boolean? = false, // false
    @SerialName("also_known_as")
    val alsoKnownAs: List<String>? = listOf(),
    @SerialName("biography")
    val biography: String? = "", // Ryan Rodney Reynolds (born October 23, 1976) is a Canadian actor and film producer. He began his career starring in the Canadian teen soap opera Hillside (1991–1993), and had minor roles before landing the lead role on the sitcom Two Guys and a Girl between 1998 and 2001. Reynolds then starred in a range of films, including comedies such as National Lampoon's Van Wilder (2002), Waiting... (2005), and The Proposal (2009). He also performed in dramatic roles in Buried (2010), Woman in Gold (2015), and Life (2017), starred in action films such as Blade: Trinity (2004), Green Lantern (2011), 6 Underground (2019) and Free Guy (2021), and provided voice acting in the animated features The Croods (2013), Turbo (2013), Pokémon: Detective Pikachu (2019) and The Croods: A New Age (2020).Reynolds' biggest commercial success came with the superhero films Deadpool (2016) and Deadpool 2 (2018), in which he played the title character. The former set numerous records at the time of its release for an R-rated comedy and his performance earned him nominations at the Critics' Choice Movie Awards and the Golden Globe Awards.
    @SerialName("birthday")
    val birthday: String? = "", // 1976-10-23
    @SerialName("deathday")
    val deathDay: String? = null, // null
    @SerialName("external_ids")
    val externalIds: PersonExternalIds? = PersonExternalIds(),
    @SerialName("gender")
    val gender: Int? = 0, // 2
    @SerialName("homepage")
    val homepage: String? = "", // https://www.callsupportteam.com/qsee-support/
    @SerialName("id")
    val id: Int? = 0, // 10859
    @SerialName("images")
    val images: PersonImages? = null,
    @SerialName("imdb_id")
    val imdbId: String? = "", // nm0005351
    @SerialName("known_for_department")
    val knownForDepartment: String? = "", // Acting
    @SerialName("movie_credits")
    val movieCredits: MovieCredits? = MovieCredits(),
    @SerialName("name")
    val name: String? = "", // Ryan Reynolds
    @SerialName("place_of_birth")
    val placeOfBirth: String? = "", // Vancouver, British Columbia, Canada
    @SerialName("popularity")
    val popularity: Double? = 0.0, // 79.196
    @SerialName("profile_path")
    val profilePath: String? = "", // /algQ1VEno2W9SesoArWcZTeF617.jpg
    @SerialName("translations")
    val translations: Translations? = Translations(),
    @SerialName("tv_credits")
    val tvCredits: TvCredits? = TvCredits()
) {


    fun mappedToPersonDetail(currentLanguage: String): MappedPersonDetail {
        val years = birthday?.let {
            LocalDate.parse(it)
                .yearsUntil(Clock.System.now().toLocalDateTime(TimeZone.UTC).date)
        }

        val bio = translations
            ?.translations
            ?.firstOrNull { it.iso6391 == currentLanguage }
            ?.biographyData
            ?.biography
        val aboutPairs = listOf(
            AboutMappedData(
                title = PersonAboutTitleMapper.KNOWN_FOR,
                subTitle = knownForDepartment
            ),
            AboutMappedData(
                title = PersonAboutTitleMapper.BORN_ON,
                subTitle = DateTimeManager.formatDate(birthday)
            ),
            AboutMappedData(
                title = PersonAboutTitleMapper.GENDER,
                subTitle = gender?.toString()
            ),
            AboutMappedData(
                title = PersonAboutTitleMapper.BIRTHPLACE,
                subTitle = placeOfBirth
            )
        )

        return MappedPersonDetail(
            personName = name,
            avatarPath = profilePath,
            years = years,
            bio = bio,
            knownFor = knownForDepartment,
            bornOn = DateTimeManager.formatDate(birthday),
            gender = gender?.toString(),
            birthPlace = placeOfBirth,
            externalLinks = externalIds?.mapToExternalLinks(homepage) ?: emptyList(),
            profileImages = images?.profiles?.mapNotNull { it.filePath } ?: emptyList(),
            movieCredits = movieCredits?.mappedCredits() ?: emptyMap(),
            tvCredits = tvCredits?.mappedCredits() ?: emptyMap(),
            aboutPairs = aboutPairs
        )
    }
}

@Serializable
data class PersonExternalIds(
    @SerialName("facebook_id")
    val facebookId: String? = null, // VancityReynolds
    @SerialName("freebase_id")
    val freebaseId: String? = null, // /en/ryan_reynolds
    @SerialName("freebase_mid")
    val freebaseMid: String? = null, // /m/036hf4
    @SerialName("imdb_id")
    val imdbId: String? = null, // nm0005351
    @SerialName("instagram_id")
    val instagramId: String? = null, // vancityreynolds
    @SerialName("tiktok_id")
    val tiktokId: String? = null, // vancityreynolds
    @SerialName("tvrage_id")
    val tvrageId: Int? = null, // 47752
    @SerialName("twitter_id")
    val twitterId: String? = null, // VancityReynolds
    @SerialName("wikidata_id")
    val wikidataId: String? = null, // Q192682
    @SerialName("youtube_id")
    val youtubeId: String? = null // VancityReynolds
) {
    fun mapToExternalLinks(homepage: String?): List<ExternalLink> {
        return listOfNotNull(
            facebookId?.let {
                ExternalLink.Facebook(it)
            },
            twitterId?.let {
                ExternalLink.Twitter(it)
            },
            imdbId?.let {
                ExternalLink.ImdbPersonProfile(it)
            },
            instagramId?.let {
                ExternalLink.Instagram(it)
            },
            homepage?.let {
                ExternalLink.HomePage(it)
            }
        )
    }
}

@Serializable
data class PersonImages(
    @SerialName("profiles")
    val profiles: List<Profile>? = null
)

@Serializable
data class MovieCredits(
    @SerialName("cast")
    val cast: List<PersonCast>? = emptyList(),
    @SerialName("crew")
    val crew: List<PersonCrew>? = emptyList()
) {
    fun mappedCredits(): Map<String, List<MappedMovieCredit>> {
        val delimiter = "-"
        val casts = cast?.map {
            MappedMovieCredit(
                id = it.id ?: Int.MIN_VALUE,
                movieName = it.originalTitle ?: it.title,
                department = "Acting",
                charOrJob = it.character,
                year = it.releaseDate?.split(delimiter)?.firstOrNull()
            )
        } ?: emptyList()

        val crews = crew?.map {
            MappedMovieCredit(
                id = it.id ?: Int.MIN_VALUE,
                movieName = it.originalTitle ?: it.title,
                department = it.department ?: "-",
                charOrJob = it.job,
                year = it.releaseDate?.split(delimiter)?.firstOrNull()
            )
        } ?: emptyList()

        return (casts + crews).filter {
            it.year.isNullOrEmpty().not()
        }.groupBy {
            it.department
        }.mapValues {
            it.value.sortedByDescending { cre ->
                cre.year
            }
        }
    }
}

@Serializable
data class Translations(
    @SerialName("translations")
    val translations: List<Translation>? = null
)

@Serializable
data class TvCredits(
    @SerialName("cast")
    val cast: List<PersonCast>? = emptyList(),
    @SerialName("crew")
    val crew: List<PersonCrew>? = emptyList()
) {
    fun mappedCredits(): Map<String, List<MappedTvCredit>> {
        val delimiter = "-"
        val casts = cast?.map {
            MappedTvCredit(
                id = it.id ?: Int.MIN_VALUE,
                tvName = it.originalName ?: it.name,
                department = "Acting",
                charOrJob = it.character,
                year = it.firstAirDate?.split(delimiter)?.firstOrNull(),
                episodeCount = it.episodeCount
            )
        } ?: emptyList()

        val crews = crew?.map {
            MappedTvCredit(
                id = it.id ?: Int.MIN_VALUE,
                tvName = it.originalName ?: it.name,
                department = it.department ?: "",
                charOrJob = it.job,
                year = it.firstAirDate?.split(delimiter)?.firstOrNull(),
                episodeCount = it.episodeCount
            )

        } ?: emptyList()

        return (casts + crews).filter {
            it.year.isNullOrEmpty().not()
        }.groupBy {
            it.department
        }.mapValues {
            it.value.sortedByDescending { cre ->
                cre.year
            }
        }
    }
}

@Serializable
data class Profile(
    @SerialName("aspect_ratio")
    val aspectRatio: Double? = null, // 0.667
    @SerialName("file_path")
    val filePath: String? = null, // /algQ1VEno2W9SesoArWcZTeF617.jpg
    @SerialName("height")
    val height: Int? = null, // 2641
    @SerialName("iso_639_1")
    val iso6391: String? = null, // null
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 5.364
    @SerialName("vote_count")
    val voteCount: Int? = null, // 31
    @SerialName("width")
    val width: Int? = null // 1761
)

@Serializable
data class PersonCast(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /wvqdJLVh0mSblly7UnYFPEk04Wd.jpg
    @SerialName("character")
    val character: String? = null, // Wade Wilson
    @SerialName("credit_id")
    val creditId: String? = null, // 52fe4333c3a36847f8041e23
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null, // 2080
    @SerialName("order")
    val order: Int? = null, // 8
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // X-Men Origins: Wolverine
    @SerialName("overview")
    val overview: String? = null, // After seeking to live a normal life, Logan sets out to avenge the death of his girlfriend by undergoing the mutant Weapon X program and becoming Wolverine.
    @SerialName("popularity")
    val popularity: Double? = null, // 5.583
    @SerialName("poster_path")
    val posterPath: String? = null, // /yj8LbTju1p7CUJg7US2unSBk33s.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 2009-04-28
    @SerialName("title")
    val title: String? = null, // X-Men Origins: Wolverine
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 6.275
    @SerialName("vote_count")
    val voteCount: Int? = null, // 10421

    @SerialName("first_air_date")
    val firstAirDate: String? = null, // 2009-04-28
    @SerialName("name")
    val name: String? = null, // X-Men Origins: Wolverine
    @SerialName("original_name")
    val originalName: String? = null, // X-Men Origins: Wolverine
    @SerialName("episode_count")
    val episodeCount: Int? = null, // X-Men Origins: Wolverine
)

@Serializable
data class PersonCrew(
    @SerialName("adult")
    val adult: Boolean? = null, // false
    @SerialName("backdrop_path")
    val backdropPath: String? = null, // /ckrpWAApQsOPpsivpJeg6odsgsM.jpg
    @SerialName("credit_id")
    val creditId: String? = null, // 52fe4981c3a368484e12ec9f
    @SerialName("department")
    val department: String? = null, // Production
    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerialName("id")
    val id: Int? = null, // 77880
    @SerialName("job")
    val job: String? = null, // Executive Producer
    @SerialName("original_language")
    val originalLanguage: String? = null, // en
    @SerialName("original_title")
    val originalTitle: String? = null, // The Whale
    @SerialName("overview")
    val overview: String? = null, // The true story of a young, wild killer whale - an orca - nicknamed Luna, who lost contact with his family on the coast of British Columbia and turned up alone in a narrow stretch of sea between mountains, a place called Nootka Sound.
    @SerialName("popularity")
    val popularity: Double? = null, // 5.086
    @SerialName("poster_path")
    val posterPath: String? = null, // /kpzNLHeVLqdLNSSCYVXKPpWoOno.jpg
    @SerialName("release_date")
    val releaseDate: String? = null, // 2011-09-09
    @SerialName("title")
    val title: String? = null, // The Whale
    @SerialName("video")
    val video: Boolean? = null, // false
    @SerialName("vote_average")
    val voteAverage: Double? = null, // 7.494
    @SerialName("vote_count")
    val voteCount: Int? = null, // 17

    @SerialName("first_air_date")
    val firstAirDate: String? = null, // 2009-04-28
    @SerialName("name")
    val name: String? = null, // X-Men Origins: Wolverine
    @SerialName("original_name")
    val originalName: String? = null, // X-Men Origins: Wolverine
    @SerialName("episode_count")
    val episodeCount: Int? = null, // X-Men Origins: Wolverine
)

@Serializable
data class Translation(
    @SerialName("data")
    val biographyData: BiographyData? = null,
    @SerialName("english_name")
    val englishName: String? = null, // English
    @SerialName("iso_3166_1")
    val iso31661: String? = null, // US
    @SerialName("iso_639_1")
    val iso6391: String? = null, // en
    @SerialName("name")
    val name: String? = null // English
)

@Serializable
data class BiographyData(
    @SerialName("biography")
    val biography: String? = null // Ryan Rodney Reynolds (born October 23, 1976) is a Canadian actor and film producer. He began his career starring in the Canadian teen soap opera Hillside (1991–1993), and had minor roles before landing the lead role on the sitcom Two Guys and a Girl between 1998 and 2001. Reynolds then starred in a range of films, including comedies such as National Lampoon's Van Wilder (2002), Waiting... (2005), and The Proposal (2009). He also performed in dramatic roles in Buried (2010), Woman in Gold (2015), and Life (2017), starred in action films such as Blade: Trinity (2004), Green Lantern (2011), 6 Underground (2019) and Free Guy (2021), and provided voice acting in the animated features The Croods (2013), Turbo (2013), Pokémon: Detective Pikachu (2019) and The Croods: A New Age (2020).Reynolds' biggest commercial success came with the superhero films Deadpool (2016) and Deadpool 2 (2018), in which he played the title character. The former set numerous records at the time of its release for an R-rated comedy and his performance earned him nominations at the Critics' Choice Movie Awards and the Golden Globe Awards.
)

@Immutable
data class MappedPersonDetail(
    val personName: String?,
    val avatarPath: String?,
    val years: Int?,
    val bio: String?,
    val externalLinks: List<ExternalLink> = emptyList(),
    val profileImages: List<String> = emptyList(),
    val movieCredits: Map<String, List<MappedMovieCredit>> = emptyMap(),
    val tvCredits: Map<String, List<MappedTvCredit>> = emptyMap(),
    val aboutPairs: List<AboutMappedData> = emptyList(),
    val bornOn: String,
    val knownFor: String?,
    val birthPlace: String?,
    val gender: String?,
)

@Immutable
data class MappedMovieCredit(
    val id: Int,
    val movieName: String?,
    val department: String,
    val charOrJob: String?,
    val year: String?
)

@Immutable
data class MappedTvCredit(
    val id: Int,
    val tvName: String?,
    val department: String,
    val charOrJob: String?,
    val year: String?,
    val episodeCount: Int?
)

@Immutable
data class AboutMappedData(
    val title: PersonAboutTitleMapper,
    val subTitle: String?
)


enum class GenderIdentifier(private val value: String) {
    NONE("unknown_gender.png"),
    FEMALE("female.png"),
    MALE("male.png"),
    NON_BINARY("non_binary.png");

    companion object {
        fun gender(value: Int?): String {
            val mapValue = value ?: 0
            return values()[mapValue].value
        }
    }
}

enum class PersonAboutTitleMapper(
    private val value: String,
    val res: StringResource
) {
    KNOWN_FOR("Known For", Res.string.knownFor),
    BORN_ON("Born on", Res.string.bornOn),
    GENDER("Gender", Res.string.gender),
    BIRTHPLACE("Birthplace", Res.string.birthPlace);
}