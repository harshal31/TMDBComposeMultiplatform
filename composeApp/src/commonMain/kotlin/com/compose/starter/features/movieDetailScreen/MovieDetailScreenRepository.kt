package com.compose.starter.features.movieDetailScreen

import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.NetworkManager
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.AccountState
import com.compose.starter.networking.model.FavoriteBody
import com.compose.starter.networking.model.FavoriteWatchlistResult
import com.compose.starter.networking.model.Images
import com.compose.starter.networking.model.TmdbMediaDetail
import com.compose.starter.networking.model.WatchlistBody
import com.compose.starter.networking.parseResponse
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.io.IOException

class MovieDetailScreenRepository(
    private val network: NetworkManager,
    private val store: LocalStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    fun getAllMovieDetails(
        movieId: String,
        language: String = DefaultParameter.LANG_CODE,
    ): Flow<Result<TmdbMediaDetail>> {
        return flow {
            emit(
                network.call.get(Endpoint.MOVIE_DETAIL) {
                    url {
                        appendPathSegments(movieId)
                        parameters.append(
                            Parameter.APPEND_TO_RESPONSE,
                            Parameter.MOVIE_MEDIA_APPEND_RESPONSE
                        )
                        parameters.append(Parameter.LANGUAGE, language)
                    }
                }.parseResponse<TmdbMediaDetail>()
            )
        }.catch {
            when (it) {
                is IOException -> emit(Result.failure(Error(ApiState.NETWORK_ISSUE.value)))
                else -> emit(Result.failure(Error(ApiState.ERROR.value)))
            }
        }.flowOn(dispatcher)
    }

    fun getImages(
        movieId: String,
        language: String = DefaultParameter.LANG_CODE,
    ): Flow<Result<Images>> {
        return flow {
            emit(
                network.call.get(Endpoint.IMAGES) {
                    url {
                        appendPathSegments(movieId)
                        parameters.append(Parameter.LANGUAGE, language)
                    }
                }.parseResponse<Images>()
            )
        }.catch {
            when (it) {
                is IOException -> emit(Result.failure(Error(com.compose.starter.networking.ApiState.NETWORK_ISSUE.value)))
                else -> emit(Result.failure(Error(com.compose.starter.networking.ApiState.ERROR.value)))
            }
        }.flowOn(dispatcher)
    }


    fun getAccountStates(movieId: String, sessionId: String?): Flow<Result<AccountState>> {
        return flow {
            emit(
                network.call.get(Endpoint.MOVIE_DETAIL) {
                    url {
                        appendPathSegments(movieId, Endpoint.ACCOUNT_STATE)
                        parameters.append(Parameter.SESSION_ID, sessionId ?: "")
                    }
                }.parseResponse<AccountState>()
            )
        }.catch {
            when (it) {
                is IOException -> emit(Result.failure(Error(com.compose.starter.networking.ApiState.NETWORK_ISSUE.value)))
                else -> emit(Result.failure(Error(com.compose.starter.networking.ApiState.ERROR.value)))
            }
        }.flowOn(dispatcher)
    }


    fun addToFavorite(
        mediaType: String,
        mediaId: String,
        sessionId: String?,
        accountId: String?,
        isFavorite: Boolean,
    ): Flow<Boolean> {
        return flow {
            val statusCode = network.call.post(Endpoint.ACCOUNT) {
                url {
                    accountId?.let { acc ->
                        appendPathSegments(acc, Parameter.FAVORITE)
                    } ?: run {
                        appendPathSegments(Parameter.FAVORITE)
                    }

                    parameters.append(Parameter.SESSION_ID, sessionId ?: "")
                }

                setBody(
                    FavoriteBody(
                        mediaType,
                        mediaId,
                        isFavorite,
                    )
                )
            }.parseResponse<FavoriteWatchlistResult>().getOrNull()?.statusCode

            emit(statusCode == ADDED || statusCode == WATCHLIST_UPDATED)
        }.catch {
            when (it) {
                is IOException -> emit(false)
                else -> emit(false)
            }
        }.flowOn(dispatcher)
    }

    fun addToWatchList(
        mediaType: String,
        mediaId: String,
        sessionId: String?,
        accountId: String?,
        shouldAddToWatchList: Boolean,
    ): Flow<Boolean> {
        return flow {
            val statusCode = network.call.post(Endpoint.ACCOUNT) {
                url {
                    appendPathSegments(accountId ?: "", Parameter.WATCHLIST)
                    parameters.append(Parameter.SESSION_ID, sessionId ?: "")
                }

                setBody(
                    WatchlistBody(
                        mediaType,
                        mediaId,
                        shouldAddToWatchList,
                    )
                )
            }.parseResponse<FavoriteWatchlistResult>().getOrNull()?.statusCode
            emit(statusCode == ADDED || statusCode == WATCHLIST_UPDATED)
        }.catch {
            when (it) {
                is IOException -> emit(false)
                else -> emit(false)
            }
        }.flowOn(dispatcher)
    }

    fun addRating(
        rating: Int,
        movieId: String,
        sessionId: String?,
    ): Flow<Boolean> {
        return flow {

            val statusCode = network.call.post(Endpoint.MOVIE_DETAIL) {
                url {
                    appendPathSegments(movieId, Endpoint.RATING)
                    sessionId?.let {
                        parameters.append(Parameter.SESSION_ID, sessionId)
                    }
                }

                setBody(mapOf(Parameter.VALUE to rating * 2))
            }.parseResponse<FavoriteWatchlistResult>().getOrNull()?.statusCode

            emit(statusCode == ADDED || statusCode == RATING_UPDATED)
        }.catch {
            when (it) {
                is IOException -> emit(false)
                else -> emit(false)
            }
        }.flowOn(dispatcher)
    }


    fun getSessionId() = store.getAsyncString(LocalStore.SESSION_ID)


    private companion object {
        const val ADDED = 1
        const val RATING_UPDATED = 12
        const val WATCHLIST_UPDATED = 13
    }
}


