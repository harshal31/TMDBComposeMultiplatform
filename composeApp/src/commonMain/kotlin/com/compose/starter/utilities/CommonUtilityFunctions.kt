package com.compose.starter.utilities

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import io.github.aakira.napier.Napier
import io.ktor.util.StringValues
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.PluralStringResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.pluralStringResource
import org.jetbrains.compose.resources.stringResource


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

fun String?.formatDate(): String {
    return try {
        val instant = Instant.parse(this ?: "")
        val localDateTime = instant.toLocalDateTime(TimeZone.UTC)
        val date = localDateTime.month.name.mapIndexed { index, c ->
            if (index > 0) c.lowercase() else c.uppercase()
        }.take(3).joinToString("")
        return "${localDateTime.dayOfMonth}-$date-${localDateTime.year}"
    } catch (t: Throwable) {
        Napier.d("date is not formatted  ${t.message}")
        ""
    }
}

@Composable
fun pluralResource(
    res: Pair<StringResource, PluralStringResource>,
    quantity: Int,
    formatArgs: Array<Any> = emptyArray(),
): String {
    return if (quantity == 0) {
        stringResource(res.first)
    } else if (formatArgs.isEmpty()) {
        pluralStringResource(res.second, quantity)
    } else {
        pluralStringResource(res.second, quantity, *formatArgs)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
fun CoroutineScope.showBottomSheet(sheetState: SheetState, onExpand: (Boolean) -> Unit) {
    launch {
        sheetState.expand()
        onExpand.invoke(true)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
fun CoroutineScope.hideBottomSheet(sheetState: SheetState, onHide: (Boolean) -> Unit) {
    launch {
        sheetState.hide()
    }.invokeOnCompletion {
        onHide(false)
    }
}