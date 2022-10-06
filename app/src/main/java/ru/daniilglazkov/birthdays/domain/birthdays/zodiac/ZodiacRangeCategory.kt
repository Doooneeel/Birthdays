package ru.daniilglazkov.birthdays.domain.birthdays.zodiac

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.core.resources.ProvideString
import ru.daniilglazkov.birthdays.domain.core.TextFormat
import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
abstract class ZodiacRangeCategory(
    private val ordinal: Int,
    private val resourceId: Int,
    vararg range: IntRange,
) : RangeCategory.Abstract<Int>(*range),
    Comparable<ZodiacRangeCategory>,
    TextFormat<ProvideString>
{
    override fun compareTo(other: ZodiacRangeCategory): Int = other.ordinal - ordinal
    override fun format(source: ProvideString): String = source.string(resourceId)

    object Aries       : ZodiacRangeCategory(ordinal = 0,  R.string.aries, 80..109)
    object Taurus      : ZodiacRangeCategory(ordinal = 1,  R.string.aries,110..140)
    object Gemini      : ZodiacRangeCategory(ordinal = 2,  R.string.taurus,141..171)
    object Cancer      : ZodiacRangeCategory(ordinal = 3,  R.string.gemini,172..203)
    object Leo         : ZodiacRangeCategory(ordinal = 4,  R.string.cancer,204..234)
    object Virgo       : ZodiacRangeCategory(ordinal = 5,  R.string.leo,235..265)
    object Libra       : ZodiacRangeCategory(ordinal = 6,  R.string.virgo,266..296)
    object Scorpio     : ZodiacRangeCategory(ordinal = 7,  R.string.libra,297..326)
    object Sagittarius : ZodiacRangeCategory(ordinal = 8,  R.string.scorpio,327..355)
    object Capricorn   : ZodiacRangeCategory(ordinal = 9,  R.string.sagittarius,356..366, 0..19)
    object Aquarius    : ZodiacRangeCategory(ordinal = 10, R.string.capricorn, 20..50)
    object Pisces      : ZodiacRangeCategory(ordinal = 11, R.string.aquarius, 51..79)
}