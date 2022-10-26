package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 23.10.2022, 19:49
 */
sealed class ZodiacDomain(
    private val ordinal: Int,
    private val name: String,
    vararg range: IntRange,
) : RangeCategory.Abstract<Int>(*range),
    Comparable<ZodiacDomain>
{
    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(ordinal, name)

    override fun compareTo(other: ZodiacDomain): Int =
        other.ordinal - ordinal


    class Aries(name: String) : ZodiacDomain(ordinal = 0, name, 80..109)
    class Taurus(name: String) : ZodiacDomain(ordinal = 1, name, 110..140)
    class Gemini(name: String) : ZodiacDomain(ordinal = 2, name, 141..171)
    class Cancer(name: String) : ZodiacDomain(ordinal = 3, name, 172..203)
    class Leo(name: String) : ZodiacDomain(ordinal = 4, name, 204..234)
    class Virgo(name: String) : ZodiacDomain(ordinal = 5, name, 235..265)
    class Libra(name: String) : ZodiacDomain(ordinal = 6, name, 266..296)
    class Scorpio(name: String) : ZodiacDomain(ordinal = 7, name, 297..326)
    class Sagittarius(name: String) : ZodiacDomain(ordinal = 8, name, 327..355)
    class Capricorn(name: String) : ZodiacDomain(ordinal = 9, name, 356..366, 0..19)
    class Aquarius(name: String) : ZodiacDomain(ordinal = 10, name, 20..50)
    class Pisces(name: String) : ZodiacDomain(ordinal = 11, name, 51..79)


    interface Mapper<T> {
        fun map(ordinal: Int, name: String): T

        class ToHeader : Mapper<String> {
            override fun map(ordinal: Int, name: String) = name
        }
    }
}