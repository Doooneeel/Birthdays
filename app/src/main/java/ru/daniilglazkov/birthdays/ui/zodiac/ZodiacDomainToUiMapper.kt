package ru.daniilglazkov.birthdays.ui.zodiac

import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.ui.zodiac.ZodiacUi.*

/**
 * @author Danil Glazkov on 02.11.2022, 10:21
 */
interface ZodiacDomainToUiMapper : ZodiacDomain.Mapper<ZodiacUi> {

    abstract class Abstract : ZodiacDomainToUiMapper {
        protected abstract val zodiacs: List<ZodiacUi>

        override fun map(ordinal: Int, name: String): ZodiacUi =
            zodiacs.find { it.matches(ordinal) } ?: Error
    }

    class Chinese : Abstract() {
        override val zodiacs = listOf<ZodiacUi>(
            Monkey, Rooster, Dog, Pig, Rat, Ox, Tiger, Rabbit, Dragon, Snake, Horse, Goat
        )
    }

    class Greek : Abstract() {
        override val zodiacs = listOf<ZodiacUi>(Aries, Taurus , Gemini , Cancer , Leo, Virgo,
            Libra, Scorpio, Sagittarius, Capricorn, Aquarius, Pisces
        )
    }
}