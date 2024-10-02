package com.compose.starter.networking.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.jetbrains.compose.resources.DrawableResource

@Serializable
data class RequestAndValidateToken(
    @SerialName("expires_at")
    val expiresAt: String?, // 2024-08-24 11:45:21 UTC
    @SerialName("request_token")
    val requestToken: String?, // f0ddc64527cea19b6c73d65adb9ad3ee93e28e3a
    @SerialName("success")
    val success: Boolean?, // true
)

@Serializable
data class ValidSession(
    @SerialName("session_id")
    val sessionId: String? = null, // 79191836ddaa0da3df76a5ffef6f07ad6ab0c641
    @SerialName("success")
    val success: Boolean? = null, // true
)


data class TabBarItem(
    val screen: Any,
    val title: String,
    val selectedIcon: DrawableResource,
    val unselectedIcon: DrawableResource,
)
