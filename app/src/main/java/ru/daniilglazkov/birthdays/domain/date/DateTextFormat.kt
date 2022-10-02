package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.*

/**
 * @author Danil Glazkov on 10.06.2022, 16:54
 */
interface DateTextFormat {
    fun format(date: LocalDate): String

    class Year : DateTextFormat {
        override fun format(date: LocalDate): String = date.year.toString()
    }

    class Full(private val locale: Locale = Locale.getDefault()) : DateTextFormat {
        override fun format(date: LocalDate): String {
            return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                .withLocale(locale)
                .format(date)
        }
    }

    class Month(private val locale: Locale = Locale.getDefault()) : DateTextFormat {
        override fun format(date: LocalDate): String {
            return date.month.getDisplayName(TextStyle.FULL_STANDALONE, locale).replaceFirstChar {
                it.titlecase(locale)
            }
        }
    }

}