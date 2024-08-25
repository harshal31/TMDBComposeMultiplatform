package com.compose.starter.features.loginScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.model.ValidSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginScreenViewModel(private val repository: LoginScreenRepository) : ViewModel() {
    private val loginFlow = MutableStateFlow(
        Pair(false, Result.success(ValidSession()))
    )
    private val uselessFlow = MutableStateFlow(0)

    val screenState = combine(loginFlow, uselessFlow) { login, _ ->
        LoginUiState(
            isLoginSuccess = login.second.getOrNull()?.success ?: false,
            isLoading = login.first,
            result = login.second
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = LoginUiState()
    )


    fun onEvent(loginEvent: LoginEvent) {
        when (loginEvent) {
            is LoginEvent.Login -> login(loginEvent.userName, loginEvent.password)
        }
    }


    private fun login(userName: String, password: String) {
        viewModelScope.launch {
            loginFlow.update { it.copy(first = true) }
            val result = repository.login(userName, password).first()
            loginFlow.update { it.copy(false, result) }
        }
    }
}