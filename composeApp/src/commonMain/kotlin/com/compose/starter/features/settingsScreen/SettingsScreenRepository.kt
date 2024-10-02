package com.compose.starter.features.settingsScreen

import com.compose.starter.localData.LocalStore
import com.compose.starter.networking.ApiState
import com.compose.starter.networking.NetworkManager
import com.compose.starter.networking.Parameter
import com.compose.starter.networking.model.AccountDetail
import com.compose.starter.networking.parseResponse
import io.ktor.client.request.get
import io.ktor.http.appendPathSegments
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class SettingsScreenRepository(
    private val network: NetworkManager,
    private val store: LocalStore,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    fun fetchUserInformation(): Flow<Result<AccountDetail>> {
        return flow {
            val sessionId = store.getAsyncString(LocalStore.SESSION_ID).first() ?: ""
            val accountId = store.getAsyncString(LocalStore.ACCOUNT_ID).first()

            emit(
                network.call.get {
                    url {
                        appendPathSegments(Parameter.ACCOUNT, accountId ?: sessionId)
                    }
                }.parseResponse<AccountDetail>().also {
                    if (it.isSuccess) {
                        it.getOrNull()?.let { ad ->
                            store.putString(LocalStore.ACCOUNT_ID, ad.id.toString())
                        }
                    }
                }
            )
        }.catch {
            when (it) {
                is IOException -> emit(Result.failure(Error(ApiState.NETWORK_ISSUE.value)))
                else -> emit(Result.failure(Error(ApiState.ERROR.value)))
            }
        }.flowOn(dispatcher)
    }


    suspend fun updateAppTheme(themeMode: ThemeMode) {
        withContext(dispatcher) {
            store.putString(LocalStore.THEME, ThemeMode.determineThemeValue(themeMode))
        }
    }

    fun getAppThemeValue(): Flow<String?> {
        return store.getAsyncString(LocalStore.THEME)
    }


}