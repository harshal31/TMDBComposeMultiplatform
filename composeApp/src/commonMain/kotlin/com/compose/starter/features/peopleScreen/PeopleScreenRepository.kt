package com.compose.starter.features.peopleScreen

import com.compose.starter.networking.CommonTmdbApi
import com.compose.starter.networking.DefaultParameter
import com.compose.starter.networking.NetworkManager
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.TMDBMedia
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO


class PeopleScreenRepository(
    network: NetworkManager,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    private val commonApi = CommonTmdbApi(
        network,
        dispatcher
    )

    suspend fun getTrendingMedia(
        media: Parameter.Media,
        timeWindow: String,
        language: String = DefaultParameter.LANG_CODE,
    ): Result<TMDBMedia> {
        return commonApi.getTrendingMedia(
            media,
            timeWindow,
            language
        )
    }

    suspend fun getPopularMedia(
        media: Pair<String, Parameter.Media>,
        page: Int = 1,
        region: String = DefaultParameter.REGION,
        language: String = DefaultParameter.LANG_CODE,
    ): Result<TMDBMedia> {
        return commonApi.getPopularMedia(
            media,
            page,
            region,
            language
        )
    }
}

