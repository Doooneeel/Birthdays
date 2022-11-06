package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.zodiac.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain

/**
 * @author Danil Glazkov on 02.11.2022, 11:05
 */
interface BirthdayZodiacsDomain {
    fun <T> map(mapper: Mapper<T>): T

    class Base(
        private val base: ZodiacDomain,
        private val chinese: ChineseZodiacDomain
    ) : BirthdayZodiacsDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(base, chinese)
    }

    interface Mapper<T> {
        fun map(base: ZodiacDomain, chinese: ChineseZodiacDomain): T
    }
}