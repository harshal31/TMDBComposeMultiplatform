package com.compose.starter

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform