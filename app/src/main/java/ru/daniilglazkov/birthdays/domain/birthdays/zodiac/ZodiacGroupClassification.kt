package ru.daniilglazkov.birthdays.domain.birthdays.zodiac

import ru.daniilglazkov.birthdays.domain.range.RangeGroup

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
interface ZodiacGroupClassification : RangeGroup<ZodiacRangeCategory, Int> {

    class Base : RangeGroup.Abstract<ZodiacRangeCategory, Int>(), ZodiacGroupClassification {
        override val ranges: List<ZodiacRangeCategory> = listOf(
            ZodiacRangeCategory.Aries,
            ZodiacRangeCategory.Taurus,
            ZodiacRangeCategory.Gemini,
            ZodiacRangeCategory.Cancer,
            ZodiacRangeCategory.Leo,
            ZodiacRangeCategory.Virgo,
            ZodiacRangeCategory.Libra,
            ZodiacRangeCategory.Scorpio,
            ZodiacRangeCategory.Sagittarius,
            ZodiacRangeCategory.Capricorn,
            ZodiacRangeCategory.Aquarius,
            ZodiacRangeCategory.Pisces,
        )
    }
}