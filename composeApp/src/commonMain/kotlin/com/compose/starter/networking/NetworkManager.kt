package com.compose.starter.networking

import com.compose.starter.appLevelBuildInfo.AppBuildInfo
import com.compose.starter.constants.AppConstants
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class NetworkManager(
    httpClientEngine: HttpClientEngine,
    private val buildInfo: AppBuildInfo
) {
    private val _client = HttpClient(httpClientEngine) {
        defaultRequest {
            url(buildInfo.baseUrl)
            contentType(ContentType.Application.Json)
            accept(ContentType.Application.Json)
            header(HttpHeaders.Authorization, "$BEARER ${AppConstants.NET_KEY}")
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.d(message, tag = NetworkManager::class.simpleName)
                }
            }
            level = if (buildInfo.isLoggingEnabled) {
                LogLevel.ALL
            } else {
                LogLevel.NONE
            }
        }

        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    val call = _client

    companion object{
        const val BEARER = "Bearer"
    }
}