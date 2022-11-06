package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.domain.zodiac.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacUi.*

/**
 * @author Danil Glazkov on 02.11.2022, 10:21
 */
interface ChineseZodiacDomainToUiMapper : ChineseZodiacDomain.Mapper<ZodiacUi> {

    class Base : ChineseZodiacDomainToUiMapper {
        private val list = listOf<ZodiacUi>(
            Rat, Ox, Tiger, Rabbit, Dragon, Snake, Horse, Goat, Monkey, Rooster, Dog, Pig
        )
        override fun map(ordinal: Int, name: String): ZodiacUi = list.find { it.matches(ordinal) }
            ?: throw IllegalStateException("Unknown zodiac: $ordinal")
    }
}