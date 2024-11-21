package com.compose.starter.networking

import com.compose.starter.utilities.second
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpResponse.parseResponse(): Result<T> {
    val httpResponse = this
    return when (httpResponse.status) {
        HttpStatusCode.OK -> Result.success(httpResponse.body<T>())
        HttpStatusCode.RequestTimeout, HttpStatusCode.BadGateway, HttpStatusCode.ServiceUnavailable, HttpStatusCode.GatewayTimeout -> Result.failure(
            Error(ApiState.NETWORK_ISSUE.value)
        )

        HttpStatusCode.Unauthorized -> Result.failure(Error(ApiState.UNAUTHORIZED.value))
        HttpStatusCode.NotFound -> Result.failure(Error(ApiState.NOT_FOUND.value))
        HttpStatusCode.InternalServerError, HttpStatusCode.NotImplemented -> Result.failure(
            Error(
                ApiState.INTERNAL_SERVER_ERROR.value
            )
        )

        else -> Result.failure(Error(ApiState.ERROR.value))
    }
}

enum class ApiState(val value: String) {
    NETWORK_ISSUE("Please check your network connection"),
    UNAUTHORIZED("Not able to authorized please check you are logged in"),
    NOT_FOUND("File not found"),
    INTERNAL_SERVER_ERROR("Internal server error"),
    ERROR("Failed to get response"),
    CIRCULAR_LOADING(""),
    HORIZONTAL_LOADING(""),
    NONE("");

    companion object {
        fun String?.mapErrorToApiIssue(): ApiState {
            return entries.firstOrNull {
                it.value == this
            } ?: NONE
        }
    }
}

object Endpoint {
    const val REQUEST_TOKEN = "authentication/token/new"
    const val VALIDATE_TOKEN = "authentication/token/validate_with_login"
    const val CREATE_VALID_SESSION = "authentication/session/new"
    const val TRENDING = "trending"
    const val POPULAR = "popular"
    const val UPCOMING = "upcoming"
    const val TV_UPCOMING = "on_the_air"
    const val NOW_IN_THEATRES = "now_playing"
    const val AIRING_TODAY = "airing_today"
    const val IMAGES = "images"
    const val MOVIE_DETAIL = "movie"
    const val ACCOUNT_STATE = "account_states"
    const val RATING = "rating"
    const val WATCHLIST = "watchlist"
    const val FAVORITE = "favorite"
    const val ACCOUNT = "account"
    const val PERSON_DETAIL = "person"


    fun discover(type: String): String {
        return "discover/$type"
    }

    fun popularBy(type: String, ending: String): String {
        return "$type/$ending"
    }

    fun determineEndpointBy(headerKey: String, apiType: String) {
        val value = Parameter.determineMedia(apiType).value
        val endpointMap = mapOf(
            "theatre_thrills" to "hit_popular_with_now_in_theatre_api",
            "daily_trends" to "hit_daily_trending_${value}_api",
            "weekly_trends" to "hit_weekly_trending_${value}_api",
            "fan_favorites" to "hit_popular_${value}_api",
            "future_flicks" to "hit_popular_upcoming_${value}_api",
            "free_flicks" to "hit_free_${value}_api",
            "on_air" to "hit_popular_tv_air_today_api",
            "future_shows" to "hit_popular_tv_upcoming_api"
        )
    }
}

object Parameter {
    const val USERNAME = "username"
    const val PASSWORD = "password"
    const val REQUEST_TOKEN = "request_token"
    const val TIME_WINDOW = "time_window"
    const val MOVIE_ID = "movie_id"
    const val APPEND_TO_RESPONSE = "append_to_response"
    const val SESSION_ID = "session_id"
    const val MEDIA_TYPE = "media_type"
    const val MEDIA_ID = "media_id"
    const val FAVORITE = "favorite"
    const val WATCHLIST = "watchlist"
    const val VALUE = "value"
    const val PERSON_ID = "person_id"


    const val LANGUAGE = "language"
    const val IMAGES = "images"
    const val PAGE = "page"
    const val REGION = "region"
    const val TIMEZONE = "timezone"
    const val CERTIFICATION = "certification"
    const val CERTIFICATION_GTE = "certification.gte"
    const val CERTIFICATION_LTE = "certification.lte"
    const val CERTIFICATION_COUNTRY = "certification_country"
    const val INCLUDE_ADULT = "include_adult"
    const val INCLUDE_VIDEO = "include_video"
    const val PRIMARY_RELEASE_YEAR = "primary_release_year"
    const val PRIMARY_RELEASE_DATE_GTE = "primary_release_date.gte"
    const val PRIMARY_RELEASE_DATE_LTE = "primary_release_date.lte"
    const val RELEASE_DATE_GTE = "release_date.gte"
    const val RELEASE_DATE_LTE = "release_date.lte"
    const val SORT_BY = "sort_by"
    const val VOTE_AVERAGE_GTE = "vote_average.gte"
    const val VOTE_AVERAGE_LTE = "vote_average.lte"
    const val VOTE_COUNT_GTE = "vote_count.gte"
    const val VOTE_COUNT_LTE = "vote_count.lte"
    const val WATCH_REGION = "watch_region"
    const val WITH_CAST = "with_cast"
    const val WITH_COMPANIES = "with_companies"
    const val WITH_CREW = "with_crew"
    const val WITH_GENRES = "with_genres"
    const val WITH_KEYWORDS = "with_keywords"
    const val WITH_ORIGIN_COUNTRY = "with_origin_country"
    const val WITH_ORIGINAL_LANGUAGE = "with_original_language"
    const val WITH_PEOPLE = "with_people"
    const val WITH_RELEASE_TYPE = "with_release_type"
    const val WITH_RUNTIME_GTE = "with_runtime.gte"
    const val WITH_RUNTIME_LTE = "with_runtime.lte"
    const val WITH_WATCH_MONETIZATION_TYPES = "with_watch_monetization_types"
    const val WITH_WATCH_PROVIDERS = "with_watch_providers"
    const val WITHOUT_COMPANIES = "without_companies"
    const val WITHOUT_GENRES = "without_genres"
    const val WITHOUT_KEYWORDS = "without_keywords"
    const val WITHOUT_WATCH_PROVIDERS = "without_watch_providers"
    const val YEAR = "year"
    const val ACCOUNT = "account"
    const val MOVIE_MEDIA_APPEND_RESPONSE =
        "credits,external_ids,images,keywords,recommendations,release_dates,reviews,similar,videos,watch_providers"
    const val PERSON_DETAIL_APPEND_RESPONSE =
        "images,external_ids,movie_credits,tv_credits,translations"

    enum class Media(val value: String) {
        MOVIE("movie"),
        TV("tv"),
        PERSON("person"),
        NONE("")
    }

    fun determineMedia(value: String?): Media {
        return Media.entries.firstOrNull { it.value == value } ?: Media.NONE
    }
}

object DefaultParameter {
    const val LANG_CODE = "en-US"
    const val REGION = "US"
    const val FREE = "free"
    const val POPULARITY_DESC = "popularity.desc"
    const val DAILY = "day"
    const val WEEKLY = "week"

    fun defaultLanguage(delimiter: String = "-"): String {
        return LANG_CODE.split(delimiter).first()
    }

    fun defaultCountry(delimiter: String = "-"): String {
        return LANG_CODE.split(delimiter).second()
    }
}