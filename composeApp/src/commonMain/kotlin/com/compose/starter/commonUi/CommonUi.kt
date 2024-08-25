package com.compose.starter.commonUi

import androidx.compose.runtime.Composable
import com.compose.starter.networking.ApiIssue
import composestarter.composeapp.generated.resources.Res
import composestarter.composeapp.generated.resources.error
import composestarter.composeapp.generated.resources.internal_server_error
import composestarter.composeapp.generated.resources.login
import composestarter.composeapp.generated.resources.network_issue
import composestarter.composeapp.generated.resources.not_found
import composestarter.composeapp.generated.resources.unauthorized
import org.jetbrains.compose.resources.stringResource


@Composable
fun Result<Any>.FailureStringBasedOnResponse(): String {
    val message = this.fold(
        onSuccess = {
            Res.string.login
        }, onFailure = {
            when (it.message) {
                ApiIssue.NETWORK_ISSUE -> Res.string.network_issue
                ApiIssue.UNAUTHORIZED -> Res.string.unauthorized
                ApiIssue.NOT_FOUND -> Res.string.not_found
                ApiIssue.INTERNAL_SERVER_ERROR -> Res.string.internal_server_error
                ApiIssue.ERROR -> Res.string.error
                else -> Res.string.login
            }
        }
    )
    val stringMessage = stringResource(message)

    return if (stringMessage != stringResource(Res.string.login)) {
        stringMessage
    } else ""
}