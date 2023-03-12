package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.core.Matches
import java.util.*

interface ZodiacDomain : Matches<Int> {

    fun <T> map(mapper: Mapper<T>): T

    interface Mapper<T> {

        fun map(ordinal: Int, name: String): T


        class ToQuery(private val locale: Locale) : Mapper<String> {
            override fun map(ordinal: Int, name: String): String =
                name.lowercase(locale)
        }

        class ToHeader : Mapper<String> {
            override fun map(ordinal: Int, name: String) = name
        }
    }
}