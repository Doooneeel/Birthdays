package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.core.Matches

/**
 * @author Danil Glazkov on 02.11.2022, 09:27
 */
abstract class ChineseZodiacDomain(
    private val ordinal: Int,
    private val name: String,
) : Matches<Int> {

    fun <T> map(mapper: Mapper<T>): T =
        mapper.map(ordinal, name)

    override fun matches(data: Int): Boolean =
        data == ordinal

    class Monkey(name: String) : ChineseZodiacDomain(ordinal = 0, name)
    class Rooster(name: String) : ChineseZodiacDomain(ordinal = 1, name)
    class Dog(name: String) : ChineseZodiacDomain(ordinal = 2, name)
    class Pig(name: String) : ChineseZodiacDomain(ordinal = 3, name)
    class Rat(name: String) : ChineseZodiacDomain(ordinal = 4, name)
    class Ox(name: String) : ChineseZodiacDomain(ordinal = 5, name)
    class Tiger(name: String) : ChineseZodiacDomain(ordinal = 6, name)
    class Rabbit(name: String) : ChineseZodiacDomain(ordinal = 7, name)
    class Dragon(name: String) : ChineseZodiacDomain(ordinal = 8, name)
    class Snake(name: String) : ChineseZodiacDomain(ordinal = 9, name)
    class Horse(name: String) : ChineseZodiacDomain(ordinal = 10, name)
    class Goat(name: String) : ChineseZodiacDomain(ordinal = 11, name)

    interface Mapper<T> {
        fun map(ordinal: Int, name: String): T
    }
}