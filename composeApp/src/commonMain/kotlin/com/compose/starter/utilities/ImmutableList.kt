package com.compose.starter.utilities

import androidx.compose.runtime.Stable


/**
 * This wrapper data class is specifically used to create stable
 * immutable list. to avoid unnecessary recomposition kindly use
 * [immutableList] function for creating [ImmutableList] object.
 */
@Stable
data class ImmutableList<T>(
    val items: List<T> = emptyList()
)

fun <T> immutableList(items: List<T>?): ImmutableList<T> {
    return immutableList<T>().copy(items = items ?: emptyList())
}

fun <T> immutableList(): ImmutableList<T> {
    return ImmutableList()
}