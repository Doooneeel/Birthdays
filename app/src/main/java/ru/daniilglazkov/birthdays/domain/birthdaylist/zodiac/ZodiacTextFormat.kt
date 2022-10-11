package ru.daniilglazkov.birthdays.domain.birthdaylist.zodiac

import ru.daniilglazkov.birthdays.domain.core.TextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString

/**
 * @author Danil Glazkov on 05.10.2022, 22:48
 */
interface ZodiacTextFormat : TextFormat<ZodiacRangeCategory> {

    class Base(private val provideString: ProvideString) : ZodiacTextFormat {
        override fun format(source: ZodiacRangeCategory): String = source.format(provideString)
    }
}