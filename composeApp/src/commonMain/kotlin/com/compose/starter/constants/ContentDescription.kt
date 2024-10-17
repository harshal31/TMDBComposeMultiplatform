package com.compose.starter.constants

object ContentDescription {
    const val TMDB_LOGO = "tmdb_logo_image"
    const val SHOW_PASS = "Show Password"
    const val HIDE_PASS = "Hide Password"
    const val USERNAME = "Username"
    const val MOVE_TO_NEXT = "Move to next"
    const val SEARCH = "search"
    const val ARROW_BACK = "Back"
    const val PROFILE_IMAGE = "profile_image"
    const val NETWORK_ERROR = "network_error"
    const val NOT_FOUND_ERROR = "not_found_error"
    const val UNAUTHORIZED_ERROR = "unauthorized_error"
    const val INTERNAL_SERVER_ERROR = "internal_Server_error"
    const val GENERAL_ERROR = "general_error"
    const val INFO = "information"
    const val BOTTOM_SHEET_POSTER = "bottom_sheet_poster"
    const val PLAY = "play"


    private const val ADD_TO_WATCHLIST = "add_to_watchlist"
    private const val REMOVE_FROM_WATCHLIST = "remove_from_watchlist"
    private const val FAV_FILLED = "Favorite"
    private const val FAV_UN_FILLED = "Remove Favorite"

    fun contentImage(name: String): String {
        return "Content Image $name"
    }

    fun favorite(isFavorite: Boolean): String {
        return if (isFavorite) FAV_FILLED else FAV_UN_FILLED
    }

    fun watchlist(shouldAddToWatchlist: Boolean): String {
        return if (shouldAddToWatchlist) ADD_TO_WATCHLIST else REMOVE_FROM_WATCHLIST
    }

    fun personImage(name: String): String {
        return "Person $name"
    }
}