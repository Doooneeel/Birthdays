package ru.daniilglazkov.birthdays.domain.birthdays.zodiac

import ru.daniilglazkov.birthdays.domain.range.RangeCategory

/**
 * @author Danil Glazkov on 05.10.2022, 21:42
 */
sealed class ZodiacRangeCategory(
    private val ordinal: Int,
    vararg range: IntRange,
) : RangeCategory.Abstract<Int>(*range),
    Comparable<ZodiacRangeCategory>
{
    override fun compareTo(other: ZodiacRangeCategory): Int = other.ordinal - ordinal

    class Aries       : ZodiacRangeCategory(ordinal = 0, 80..109)         //Овен
    class Taurus      : ZodiacRangeCategory(ordinal = 1, 110..140)        //Телец
    class Gemini      : ZodiacRangeCategory(ordinal = 2, 141..171)        //Близнецы
    class Cancer      : ZodiacRangeCategory(ordinal = 3, 172..203)        //Рак
    class Lion        : ZodiacRangeCategory(ordinal = 4, 204..234)        //Лев
    class Virgo       : ZodiacRangeCategory(ordinal = 5, 235..265)        //Дева
    class Libra       : ZodiacRangeCategory(ordinal = 6, 266..296)        //Весы
    class Scorpion    : ZodiacRangeCategory(ordinal = 7, 297..326)        //Скорпион
    class Sagittarius : ZodiacRangeCategory(ordinal = 8, 327..355)        //Стрелец
    class Capricorn   : ZodiacRangeCategory(ordinal = 9, 356..366, 0..19) //Козерог
    class Aquarius    : ZodiacRangeCategory(ordinal = 10, 20..50)         //Водолей
    class Fish        : ZodiacRangeCategory(ordinal = 11, 51..79)         //Рыбы
}