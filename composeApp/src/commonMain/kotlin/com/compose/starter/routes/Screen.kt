package com.compose.starter.routes

import kotlinx.serialization.Serializable


object Screen {
    @Serializable
    data object LoginScreen

    @Serializable
    data object HomeScreen
}