package com.compose.starter.features.loginScreen

import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.ApiIssue
import com.compose.starter.networking.Endpoint
import com.compose.starter.networking.NetworkManager
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.RequestAndValidateToken
import com.compose.starter.networking.model.ValidSession
import com.compose.starter.networking.parseResponse
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class LoginScreenRepository(
    private val network: NetworkManager,
    private val store: LocalStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    fun login(userName: String, password: String): Flow<Result<ValidSession>> {
        return flow {
            network.call.get { }
            val requestToken =
                network.call.get(Endpoint.REQUEST_TOKEN).parseResponse<RequestAndValidateToken>()
                    .getOrNull()?.requestToken ?: ""

            val validateToken = network.call.post(Endpoint.VALIDATE_TOKEN) {
                contentType(ContentType.Application.Json)
                setBody(
                    mapOf(
                        Parameter.USERNAME to userName,
                        Parameter.PASSWORD to password,
                        Parameter.REQUEST_TOKEN to requestToken,
                    )
                )
            }.parseResponse<RequestAndValidateToken>()

            if (validateToken.isFailure) {
                emit(Result.failure(Error(ApiIssue.ERROR)))
            } else {
                store.putString(LocalStore.TOKEN_ID, requestToken)
                val validSession = network.call.post(Endpoint.CREATE_VALID_SESSION) {
                    setBody(mapOf(Parameter.REQUEST_TOKEN to requestToken))
                }.parseResponse<ValidSession>()

                if (validSession.isSuccess) {
                    store.putString(
                        LocalStore.SESSION_ID, validSession.getOrNull()?.sessionId ?: ""
                    )
                }
                emit(validSession)
            }

        }
            .catch { emit(Result.failure(Error("jkbk"))) }
            .flowOn(dispatcher)


    }
}