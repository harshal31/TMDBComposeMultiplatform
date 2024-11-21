package com.compose.starter.features.personDetailScreen

import com.compose.starter.networking.ApiState
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.NetworkManager
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.PersonDetail
import com.compose.starter.networking.parseResponse
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.io.IOException

class PersonDetailRepository(private val network: NetworkManager) {
    suspend fun fetchPersonDetails(personId: String, defaultLang: String): Result<PersonDetail> {
        return try {
            network.call.get(Endpoint.PERSON_DETAIL) {
                url {
                    appendPathSegments(personId)
                    parameters.append(
                        Parameter.APPEND_TO_RESPONSE,
                        Parameter.PERSON_DETAIL_APPEND_RESPONSE
                    )
                    parameters.append(Parameter.LANGUAGE, defaultLang)
                }
            }.parseResponse<PersonDetail>()
        }  catch (t: Throwable) {
            when (t) {
                is IOException -> Result.failure(Error(ApiState.NETWORK_ISSUE.value))
                else -> Result.failure(Error(ApiState.ERROR.value))
            }
        }
    }
}