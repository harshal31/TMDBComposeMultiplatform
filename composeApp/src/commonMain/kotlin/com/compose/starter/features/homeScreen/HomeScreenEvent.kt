package com.compose.starter.features.homeScreen

import androidx.compose.runtime.Immutable

@Immutable
data class HomeScreenState(
    val shouldDisplayAppBar: Boolean = true,
    val shouldDisplayBottomBar: Boolean = true,
)