package ru.daniilglazkov.birthdays.domain.date

import ru.daniilglazkov.birthdays.domain.core.TextFormat
import java.time.LocalDate
import java.time.format.*
import java.util.*

/**
 * @author Danil Glazkov on 10.06.2022, 16:54
 */
interface DateTextFormat : TextFormat<LocalDate> {

    abstract class Abstract(
        private val formatStyle: FormatStyle,
        private val locale: Locale
    ) : DateTextFormat {
        override fun format(source: LocalDate): String {
            return DateTimeFormatter.ofLocalizedDate(formatStyle)
                .withLocale(locale)
                .format(source)
                .replaceFirstChar { it.titlecase(locale) }
        }
    }

    class Long(locale: Locale = Locale.getDefault()) : Abstract(FormatStyle.LONG, locale)
    class Full(locale: Locale = Locale.getDefault()) : Abstract(FormatStyle.FULL, locale)

    class Year : DateTextFormat {
        override fun format(source: LocalDate): String = source.year.toString()
    }

    class Month(private val locale: Locale = Locale.getDefault()) : DateTextFormat {
        override fun format(source: LocalDate): String {
            return source.month.getDisplayName(TextStyle.FULL_STANDALONE, locale).replaceFirstChar {
                it.titlecase(locale)
            }
        }
    }

}