package ru.daniilglazkov.birthdays.domain.datetime

import ru.daniilglazkov.birthdays.domain.core.text.TextFormat
import java.time.LocalDate
import java.time.format.*
import java.util.*

/**
 * @author Danil Glazkov on 10.06.2022, 16:54
 */
interface DateTextFormat : TextFormat<LocalDate> {

    abstract class Abstract(style: FormatStyle, private val locale: Locale) : DateTextFormat {
        private val formatter = DateTimeFormatter.ofLocalizedDate(style)
            .withLocale(locale)

        override fun format(source: LocalDate) = formatter.format(source).replaceFirstChar {
            it.titlecase(locale)
        }
    }

    class Full(locale: Locale) : Abstract(FormatStyle.FULL, locale)

    class Year : DateTextFormat {
        override fun format(source: LocalDate): String = source.year.toString()
    }

    class DayOfWeek(private val locale: Locale) : DateTextFormat {
        override fun format(source: LocalDate): String =
            source.dayOfWeek.getDisplayName(TextStyle.FULL, locale).replaceFirstChar {
                it.titlecase(locale)
        }
    }

    class Month(private val locale: Locale) : DateTextFormat {
        override fun format(source: LocalDate): String =
            source.month.getDisplayName(TextStyle.FULL_STANDALONE, locale).replaceFirstChar {
                it.titlecase(locale)
            }
    }

    class MonthAndYearOfNextEvent(
        private val nextEvent: CalculateNextEvent,
        locale: Locale
    ) : DateTextFormat {
        private val month: DateTextFormat = Month(locale)

        override fun format(source: LocalDate): String =
            "${ month.format(source) } ${ nextEvent.nextEvent(source).year }"
    }

    data class Mock(var result: String = "") : DateTextFormat {
        override fun format(source: LocalDate): String = result
    }
}