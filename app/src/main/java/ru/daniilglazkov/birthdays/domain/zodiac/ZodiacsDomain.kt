package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain

/**
 * @author Danil Glazkov on 02.11.2022, 11:05
 */
interface ZodiacsDomain {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {
        fun map(greek: ZodiacDomain, chinese: ZodiacDomain): T
    }


    abstract class Abstract(
        private val greek: ZodiacDomain,
        private val chinese: ZodiacDomain,
    ) : ZodiacsDomain {
        override fun <T> map(mapper: Mapper<T>): T =
            mapper.map(greek, chinese)
    }

    data class Base(
        private val greek: ZodiacDomain,
        private val chinese: ZodiacDomain,
    ) : Abstract(greek, chinese)

    data class Mock(
        private val greek: ZodiacDomain = GreekZodiacDomain.Base(1, "a"),
        private val chinese: ZodiacDomain = ChineseZodiacDomain.Base(2, "b")
    ) : Abstract(greek, chinese)
}