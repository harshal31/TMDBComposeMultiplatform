package com.compose.starter.commonUi

import androidx.compose.runtime.Composable
import com.compose.starter.networking.ApiState
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
                ApiState.NETWORK_ISSUE.value -> Res.string.network_issue
                ApiState.UNAUTHORIZED.value -> Res.string.unauthorized
                ApiState.NOT_FOUND.value -> Res.string.not_found
                ApiState.INTERNAL_SERVER_ERROR.value -> Res.string.internal_server_error
                ApiState.ERROR.value -> Res.string.error
                else -> Res.string.login
            }
        }
    )
    val stringMessage = stringResource(message)

    return if (stringMessage != stringResource(Res.string.login)) {
        stringMessage
    } else ""
}
