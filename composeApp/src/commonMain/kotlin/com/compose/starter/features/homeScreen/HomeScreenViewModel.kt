package com.compose.starter.features.homeScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class HomeScreenViewModel : ViewModel() {

    var uiState = MutableStateFlow(HomeScreenState())

    fun onEvent(state: HomeScreenEvent) {
        when (state) {
            HomeScreenEvent.HideAppBar -> uiState.update {
                it.copy(shouldDisplayAppBar = false)
            }

            HomeScreenEvent.ShowAppBar -> uiState.update {
                it.copy(
                    shouldDisplayAppBar = true,
                    statusBarColor = null
                )
            }

            is HomeScreenEvent.UpdateStatusColor -> uiState.update {
                it.copy(statusBarColor = state.statusBarColor)
            }
        }
    }
}