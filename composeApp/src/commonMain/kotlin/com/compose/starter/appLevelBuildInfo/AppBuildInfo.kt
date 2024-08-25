package com.compose.starter.appLevelBuildInfo

data class AppBuildInfo(
    var isLoggingEnabled: Boolean = true,
    val baseUrl: String = " https://api.themoviedb.org/3/"
)
