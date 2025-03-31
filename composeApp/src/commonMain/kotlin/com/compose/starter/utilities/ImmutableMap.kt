package com.compose.starter.utilities

import androidx.compose.runtime.Stable


/**
 * This wrapper data class is specifically used to create stable
 * immutable list. to avoid unnecessary recomposition kindly use
 * [ImmutableMap] function for creating [ImmutableMap] object.
 */
@Stable
data class ImmutableMap<K, V>(
    val map: Map<K, V> = emptyMap()
)

fun <K, V> immutableMap(items: Map<K, V>?): ImmutableMap<K, V> {
    return immutableMap<K, V>().copy(map = items ?: emptyMap())
}

fun <K, V> immutableMap(): ImmutableMap<K, V> {
    return ImmutableMap()
}
