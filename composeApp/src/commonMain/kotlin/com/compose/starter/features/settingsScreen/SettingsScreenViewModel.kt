package com.compose.starter.features.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.compose.starter.networking.ApiState.Companion.mapErrorToApiIssue
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    private val repository: SettingsScreenRepository,
) : ViewModel() {

    val uiState = combine(
        repository.fetchUserInformation(),
        repository.getAppThemeValue()
    ) { state, appTheme ->
        SettingScreenUiState(
            theme = ThemeMode.determineThemeMode(appTheme),
            apiState = state.exceptionOrNull()?.message.mapErrorToApiIssue(),
            accountDetail = state.getOrNull()
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = SettingScreenUiState()
    )


    fun updateTheme(value: String) {
        viewModelScope.launch {
            repository.updateAppTheme(
                ThemeMode.determineThemeMode(
                    (ThemeMode.isDarkTheme(value) ?: false).not()
                )
            )
        }
    }
}