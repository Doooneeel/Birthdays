package ru.daniilglazkov.birthdays.domain.zodiac.greek

import ru.daniilglazkov.birthdays.domain.range.Range
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain

/**
 * @author Danil Glazkov on 23.10.2022, 19:49
 */
abstract class GreekZodiacDomain(
    private val ordinal: Int,
    private val name: String,
    vararg ranges: IntRange
) : Range.Abstract<Int>(*ranges),
    Comparable<GreekZodiacDomain>,
    ZodiacDomain
{
    override fun <T> map(mapper: ZodiacDomain.Mapper<T>): T = mapper.map(ordinal, name)

    override fun matches(data: Int): Boolean = data == ordinal

    override fun compareTo(other: GreekZodiacDomain): Int =
        ordinal - other.ordinal


    data class Base(
        private val ordinal: Int,
        private val name: String,
        private val daysOfYear: List<IntRange>
    ) : GreekZodiacDomain(ordinal, name, *daysOfYear.toTypedArray()) {
        constructor(ordinal: Int, name: String, vararg daysOfYear: IntRange) : this(
            ordinal, name, daysOfYear.toList()
        )
    }

    class Aries(name: String) : GreekZodiacDomain(ordinal = 0, name, 80..110)
    class Taurus(name: String) : GreekZodiacDomain(ordinal = 1, name, 111..141)
    class Gemini(name: String) : GreekZodiacDomain(ordinal = 2, name, 142..172)
    class Cancer(name: String) : GreekZodiacDomain(ordinal = 3, name, 173..203)
    class Leo(name: String) : GreekZodiacDomain(ordinal = 4, name, 204..235)
    class Virgo(name: String) : GreekZodiacDomain(ordinal = 5, name, 236..266)
    class Libra(name: String) : GreekZodiacDomain(ordinal = 6, name, 267..296)
    class Scorpio(name: String) : GreekZodiacDomain(ordinal = 7, name, 297..326)
    class Sagittarius(name: String) : GreekZodiacDomain(ordinal = 8, name, 327..356)
    class Capricorn(name: String) : GreekZodiacDomain(ordinal = 9, name, 357..366, 1..20)
    class Aquarius(name: String) : GreekZodiacDomain(ordinal = 10, name, 21..50)
    class Pisces(name: String) : GreekZodiacDomain(ordinal = 11, name, 51..79)
}