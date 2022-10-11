package ru.daniilglazkov.birthdays.domain.showmode.zodiac

import ru.daniilglazkov.birthdays.domain.showmode.zodiac.ZodiacRangeCategory.*
import ru.daniilglazkov.birthdays.domain.range.RangeGroup

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
interface ZodiacGroupClassification : RangeGroup<ZodiacRangeCategory, Int> {

    class Base : RangeGroup.Abstract<ZodiacRangeCategory, Int>(), ZodiacGroupClassification {
        override val ranges = listOf(Aries, Taurus, Gemini, Cancer, Leo, Virgo, Libra,
            Scorpio, Sagittarius, Capricorn, Aquarius, Pisces
        )
    }
}