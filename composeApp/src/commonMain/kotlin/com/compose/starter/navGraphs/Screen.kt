package com.compose.starter.navGraphs

import kotlinx.serialization.Serializable

object Screen {
    @Serializable
    data object LoginScreen

    @Serializable
    data object HomeScreen
}