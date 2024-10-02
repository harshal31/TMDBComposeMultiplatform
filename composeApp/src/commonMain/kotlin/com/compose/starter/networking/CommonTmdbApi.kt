package com.compose.starter.networking

import com.compose.starter.networking.model.TMDBMedia
import com.compose.starter.utilities.toStringValues
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class CommonTmdbApi(
    private val network: NetworkManager,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend fun getTrendingMedia(
        media: Parameter.Media,
        timeWindow: String,
        language: String = DefaultParameter.LANG_CODE,
    ): Result<TMDBMedia> {
        return withContext(dispatcher) {
            try {
                network.call.get(Endpoint.TRENDING) {
                    url {
                        appendPathSegments(media.value, timeWindow)
                        parameters.append(Parameter.LANGUAGE, language)
                    }
                }.parseResponse<TMDBMedia>()
            } catch (e: Throwable) {
                handleException(e)
            }
        }
    }


    suspend fun getPopularMedia(
        media: Pair<String, Parameter.Media>,
        page: Int = 1,
        region: String = DefaultParameter.REGION, // https://developer.themoviedb.org/reference/configuration-countries
        language: String = DefaultParameter.LANG_CODE,
    ): Result<TMDBMedia> {
        return withContext(dispatcher) {
            try {
                val (ending, type) = media
                network.call.get(
                    Endpoint.popularBy(type = type.value, ending = ending)
                ) {
                    url {
                        parameters.append(Parameter.LANGUAGE, language)
                        parameters.append(Parameter.PAGE, page.toString())
                        parameters.append(Parameter.REGION, region)
                    }
                }.parseResponse<TMDBMedia>()
            } catch (e: Throwable) {
                handleException(e)
            }
        }
    }

    suspend fun freeToWatchMedia(
        media: Parameter.Media,
        region: String = DefaultParameter.REGION,
    ): Result<TMDBMedia> {
        return withContext(dispatcher) {
            try {
                network.call.get(Endpoint.discover(media.value)) {
                    url {
                        parameters.appendAll(
                            mapOf(
                                Parameter.WITH_WATCH_MONETIZATION_TYPES to DefaultParameter.FREE,
                                Parameter.REGION to region,
                                Parameter.SORT_BY to DefaultParameter.POPULARITY_DESC,
                            ).toStringValues()
                        )
                    }
                }.parseResponse<TMDBMedia>()
            } catch (e: Throwable) {
                handleException(e)
            }
        }
    }

    private fun <T> handleException(it: Throwable): Result<T> {
        return when (it) {
            is IOException -> Result.failure(Error(ApiState.NETWORK_ISSUE.value))
            else -> Result.failure(Error(ApiState.ERROR.value))
        }
    }
}