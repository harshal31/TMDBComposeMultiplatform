package com.compose.starter.utilities

import io.github.aakira.napier.Napier
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.FormatStringsInDatetimeFormats
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.byUnicodePattern
import kotlinx.datetime.toLocalDateTime

object DateTimeManager {
    @OptIn(FormatStringsInDatetimeFormats::class)
    fun formatDate(
        date: String?,
        inputFormat: String = "yyyy-MM-dd",
        delimiter: String = "-"
    ): String {

        if (date.isNullOrEmpty() || inputFormat.isEmpty()) {
            Napier.e(
                tag = "DateTimeManager.formatDate()",
                message = "Please check if entered date/inputFormat is not empty or null"
            )
        }

        return runCatching {
            LocalDate.parse(date ?: "", LocalDate.Format { byUnicodePattern(inputFormat) }).format(
                LocalDate.Format {
                    date(
                        LocalDate.Format {
                            dayOfMonth()
                            chars(delimiter)
                            monthName(MonthNames.ENGLISH_ABBREVIATED)
                            chars(delimiter)
                            year()
                        }
                    )
                }
            )
        }.recoverCatching {
            Instant.parse(date ?: "").toLocalDateTime(TimeZone.UTC).format(
                LocalDateTime.Format {
                    dateTime(
                        LocalDateTime.Format {
                            dayOfMonth()
                            chars(delimiter)
                            monthName(MonthNames.ENGLISH_ABBREVIATED)
                            chars(delimiter)
                            year()
                        }
                    )
                }
            )
        }.getOrDefault("")
    }
}