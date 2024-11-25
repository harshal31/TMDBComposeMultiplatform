package com.compose.starter.constants

object AppConstants {

    const val NET_KEY = ""

    const val FULL_SIZE_BASE_URL = "https://image.tmdb.org/t/p/original"
    const val CROP_SIZE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun youtubeThumbnailImage(key: String): String {
        return "https://i.ytimg.com/vi/$key/maxresdefault.jpg"
    }
}
