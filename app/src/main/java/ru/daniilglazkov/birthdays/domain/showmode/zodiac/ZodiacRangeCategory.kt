package ru.daniilglazkov.birthdays.domain.showmode.zodiac

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
abstract class ZodiacRangeCategory(
    private val ordinal: Int,
    private val birthdayType: BirthdayType.Zodiac,
    vararg range: IntRange,
) : RangeCategory.Abstract<Int>(*range),
    Comparable<ZodiacRangeCategory>,
    FetchZodiacBirthdayType
{
    override fun compareTo(other: ZodiacRangeCategory): Int = other.ordinal - ordinal
    override fun fetchType(): BirthdayType.Zodiac = birthdayType

    object Aries       : ZodiacRangeCategory(ordinal = 0, BirthdayType.Zodiac.Aries,80..109)
    object Taurus      : ZodiacRangeCategory(ordinal = 1, BirthdayType.Zodiac.Taurus,110..140)
    object Gemini      : ZodiacRangeCategory(ordinal = 2, BirthdayType.Zodiac.Gemini,141..171)
    object Cancer      : ZodiacRangeCategory(ordinal = 3, BirthdayType.Zodiac.Cancer,172..203)
    object Leo         : ZodiacRangeCategory(ordinal = 4, BirthdayType.Zodiac.Leo,204..234)
    object Virgo       : ZodiacRangeCategory(ordinal = 5, BirthdayType.Zodiac.Virgo,235..265)
    object Libra       : ZodiacRangeCategory(ordinal = 6, BirthdayType.Zodiac.Libra,266..296)
    object Scorpio     : ZodiacRangeCategory(ordinal = 7, BirthdayType.Zodiac.Scorpio,297..326)
    object Sagittarius : ZodiacRangeCategory(ordinal = 8, BirthdayType.Zodiac.Sagittarius,327..355)
    object Capricorn   : ZodiacRangeCategory(ordinal = 9, BirthdayType.Zodiac.Capricorn,356..366, 0..19)
    object Aquarius    : ZodiacRangeCategory(ordinal = 10, BirthdayType.Zodiac.Aquarius, 20..50)
    object Pisces      : ZodiacRangeCategory(ordinal = 11, BirthdayType.Zodiac.Pisces, 51..79)
}

interface FetchZodiacBirthdayType {
    fun fetchType(): BirthdayType.Zodiac
}