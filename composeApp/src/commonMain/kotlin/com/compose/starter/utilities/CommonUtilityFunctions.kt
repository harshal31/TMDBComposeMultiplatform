package com.compose.starter.utilities

import io.ktor.util.StringValues

fun Map<String, Any>.toStringValues(): StringValues {
    return StringValues.build {
        this@toStringValues.forEach { (key, value) ->
            append(key, value.toString())
        }
    }
}

fun <T> List<T>.secondOrNull(): T? {
    return if (isEmpty()) null else this[1]
}


fun <T> List<T>.second(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[1]
}

fun <T> List<T>.third(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[2]
}

fun <T> List<T>.fourth(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[3]
}

fun <T> List<T>.fifth(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[4]
}

fun <T> List<T>.sixth(): T {
    if (isEmpty())
        throw NoSuchElementException("List is empty.")
    return this[5]
}