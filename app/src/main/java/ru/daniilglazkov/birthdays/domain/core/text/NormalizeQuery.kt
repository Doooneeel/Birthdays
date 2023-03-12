package ru.daniilglazkov.birthdays.domain.core.text

/**
 * @author Danil Glazkov on 10.08.2022, 17:21
 */
interface NormalizeQuery {

    fun normalize(query: CharSequence): List<String>


    class SplitRegex(private val regex: Regex) : NormalizeQuery {
        override fun normalize(query: CharSequence): List<String> = query.split(regex)
    }

    class Base : NormalizeQuery {
        private val splitRegex = SplitRegex(Regex("\\s+"))

        override fun normalize(query: CharSequence): List<String> =
            splitRegex.normalize(query)
                .filter { it.isNotEmpty() }
                .map { it.trim().lowercase() }
                .distinct()
    }
}