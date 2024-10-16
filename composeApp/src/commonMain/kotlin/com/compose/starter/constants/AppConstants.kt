package com.compose.starter.constants

object AppConstants {

    const val NET_KEY =
        "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJmOTkwOTliMWJjOGY1NzVkNmU2YTgzMTkzNWU4ZGMxNSIsIm5iZiI6MTcyNDMyNzM1OS42MzIyMjgsInN1YiI6IjVlNjFmYmQ1MjJhZjNlMDAxM2RjZGU2NSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ._nT5Fs3ORccnc4PlrtwpeGsVQ8q7r9zAxX540c1BSuE"

    const val FULL_SIZE_BASE_URL = "https://image.tmdb.org/t/p/original"
    const val CROP_SIZE_BASE_URL = "https://image.tmdb.org/t/p/w500"

    fun youtubeThumbnailImage(key: String): String {
        return "https://i.ytimg.com/vi/$key/maxresdefault.jpg"
    }
}