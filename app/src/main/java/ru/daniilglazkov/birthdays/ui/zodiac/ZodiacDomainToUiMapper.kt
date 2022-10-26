package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacUi.*

/**
 * @author Danil Glazkov on 25.10.2022, 09:10
 */
interface ZodiacDomainToUiMapper : ZodiacDomain.Mapper<ZodiacUi> {

    class Base : ZodiacDomainToUiMapper {
        private val list = listOf<ZodiacUi>(Aries(), Taurus(), Gemini(), Cancer(), Leo(),
            Virgo(), Libra(), Scorpio(), Sagittarius(), Capricorn(), Aquarius(), Pisces()
        )
        override fun map(ordinal: Int, name: String): ZodiacUi =
            list.find { it.matches(ordinal) } ?: throw IllegalStateException("Unknown zodiac: $ordinal")
    }
}