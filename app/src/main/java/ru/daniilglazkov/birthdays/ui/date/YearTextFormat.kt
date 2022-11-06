package ru.daniilglazkov.birthdays.ui.date

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.core.TextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 19.07.2022, 01:49
 */
interface YearTextFormat : TextFormat<Int> {

    class Base(private val provideString: ProvideString) : YearTextFormat {
        override fun format(source: Int): String =
            provideString.quantityString(R.plurals.age, source)
    }
}