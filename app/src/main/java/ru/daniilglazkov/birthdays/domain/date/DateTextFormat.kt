package ru.daniilglazkov.birthdays.domain.date

import ru.daniilglazkov.birthdays.domain.core.TextFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.*

/**
 * @author Danil Glazkov on 10.06.2022, 16:54
 */
interface DateTextFormat : TextFormat<LocalDate> {

    class Year : DateTextFormat {
        override fun format(source: LocalDate): String = source.year.toString()
    }

    class Full(private val locale: Locale = Locale.getDefault()) : DateTextFormat {
        override fun format(source: LocalDate): String {
            return DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG)
                .withLocale(locale)
                .format(source)
        }
    }

    class Month(private val locale: Locale = Locale.getDefault()) : DateTextFormat {
        override fun format(source: LocalDate): String {
            return source.month.getDisplayName(TextStyle.FULL_STANDALONE, locale).replaceFirstChar {
                it.titlecase(locale)
            }
        }
    }

}