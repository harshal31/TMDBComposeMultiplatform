package com.compose.starter.networking

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode

suspend inline fun <reified T> HttpResponse.parseResponse(): Result<T> {
    val httpResponse = this
    return when (httpResponse.status) {
        HttpStatusCode.OK -> Result.success(httpResponse.body<T>())
        HttpStatusCode.RequestTimeout, HttpStatusCode.BadGateway, HttpStatusCode.ServiceUnavailable, HttpStatusCode.GatewayTimeout -> Result.failure(
            Error(ApiIssue.NETWORK_ISSUE)
        )

        HttpStatusCode.Unauthorized -> Result.failure(Error(ApiIssue.UNAUTHORIZED))
        HttpStatusCode.NotFound -> Result.failure(Error(ApiIssue.NOT_FOUND))
        HttpStatusCode.InternalServerError, HttpStatusCode.NotImplemented -> Result.failure(
            Error(
                ApiIssue.INTERNAL_SERVER_ERROR
            )
        )

        else -> Result.failure(Error(ApiIssue.ERROR))
    }
}

object ApiIssue {
    const val NETWORK_ISSUE = "Please check your network connection"
    const val UNAUTHORIZED = "Not able to authorized please check you are logged in"
    const val NOT_FOUND = "File not found"
    const val INTERNAL_SERVER_ERROR = "Internal server error"
    const val ERROR = "Failed to get response"
}


object Endpoint {
    const val REQUEST_TOKEN = "authentication/token/new"
    const val VALIDATE_TOKEN = "authentication/token/validate_with_login"
    const val CREATE_VALID_SESSION = "authentication/session/new"
}

object Parameter {
    val USERNAME = "username"
    val PASSWORD = "password"
    val REQUEST_TOKEN = "request_token"
}
