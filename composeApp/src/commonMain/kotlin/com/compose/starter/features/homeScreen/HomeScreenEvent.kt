package com.compose.starter.features.homeScreen

import androidx.compose.ui.graphics.Color

sealed interface HomeScreenEvent {
    data object HideAppBar : HomeScreenEvent
    data object ShowAppBar : HomeScreenEvent
    data class UpdateStatusColor(val statusBarColor: Color?) : HomeScreenEvent
}

data class HomeScreenState(
    val shouldDisplayAppBar: Boolean = true,
    val statusBarColor: Color? = null,
)