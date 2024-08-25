package com.compose.starter.features.loginScreen

import com.compose.starter.networking.model.ValidSession

data class LoginUiState(
    val isLoading: Boolean = false,
    val isLoginSuccess: Boolean = false,
    val result: Result<ValidSession> = Result.success(ValidSession()),
)


sealed interface LoginEvent {
    data class Login(val userName: String, val password: String) : LoginEvent
}