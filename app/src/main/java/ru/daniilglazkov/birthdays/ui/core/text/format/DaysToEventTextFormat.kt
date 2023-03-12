package ru.daniilglazkov.birthdays.ui.core.text.format

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.text.TextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 06.11.2022, 15:45
 */
interface DaysToEventTextFormat : TextFormat<Int> {

    abstract class Abstract(private val provideString: ProvideString) : DaysToEventTextFormat {
        override fun format(source: Int): String =
            provideString.quantityString(R.plurals.day, source)
    }

    class WithText(private val resources: ProvideString) : Abstract(resources) {
        override fun format(source: Int) = when(source) {
            0 -> resources.string(R.string.today)
            1 -> resources.string(R.string.tomorrow)
            else -> super.format(source)
        }
    }

    class OnlyNumbers(resources: ProvideString) : Abstract(resources)
}