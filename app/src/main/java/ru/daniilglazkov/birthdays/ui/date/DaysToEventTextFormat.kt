package ru.daniilglazkov.birthdays.ui.date

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.TextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 06.11.2022, 15:45
 */
interface DaysToEventTextFormat : TextFormat<Int> {

    class WithText(private val provideString: ProvideString) : DaysToEventTextFormat {
        override fun format(source: Int) = when(source) {
            0 -> provideString.string(R.string.today)
            1 -> provideString.string(R.string.tomorrow)
            else -> provideString.quantityString(R.plurals.day, source)
        }
    }

    class OnlyNumbers(private val provideString: ProvideString) : DaysToEventTextFormat {
        override fun format(source: Int): String =
            provideString.quantityString(R.plurals.day, source)
    }
}