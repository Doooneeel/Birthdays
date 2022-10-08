package ru.daniilglazkov.birthdays.domain.birthdays.search

/**
 * @author Danil Glazkov on 08.10.2022, 18:15
 */
interface PrepareQuery {
    fun prepare(query: String): String

    class Base : PrepareQuery {
        override fun prepare(query: String): String = query.trim()
            .lowercase()
    }
}
