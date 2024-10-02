package com.compose.starter.routes

import kotlinx.serialization.Serializable


object Screen {

    @Serializable
    data object LoginScreen

    @Serializable
    data object HomeScreen

    @Serializable
    data object Movies

    @Serializable
    data object TvSeries

    @Serializable
    data object Peoples

    @Serializable
    data object Settings

    @Serializable
    data class MovieDetailScreen(
        val id: String,
    )



    object Graphs {
        @Serializable
        data object MoviesGraph

        @Serializable
        data object TvSeriesGraph

        @Serializable
        data object PeoplesGraph

        @Serializable
        data object SettingsGraph
    }
}