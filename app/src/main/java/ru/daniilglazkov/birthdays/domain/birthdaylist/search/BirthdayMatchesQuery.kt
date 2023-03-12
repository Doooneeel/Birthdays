package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain

/**
 * @author Danil Glazkov on 08.10.2022, 17:12
 */
interface BirthdayMatchesQuery {

    fun matches(birthday: BirthdayDomain, queries: Collection<String>): Boolean


    abstract class Abstract(private val mapper: BirthdayDomainToQueryMapper): BirthdayMatchesQuery {

        protected abstract fun matches(source: String, query: String) : Boolean

        override fun matches(birthday: BirthdayDomain, queries: Collection<String>): Boolean {
            val source: Collection<String> = birthday.map(mapper)

            val isNotEmpty = queries.isNotEmpty() && source.isNotEmpty()

            return isNotEmpty && queries.all { query: String ->
                source.any { birthday: String ->
                    matches(birthday, query)
                }
            }
        }
    }

    class IncompleteMatch(mapper: BirthdayDomainToQueryMapper) : Abstract(mapper) {
        override fun matches(source: String, query: String): Boolean = source.contains(query)
    }

    class CompleteMatches(mapper: BirthdayDomainToQueryMapper) : Abstract(mapper) {
        override fun matches(source: String, query: String): Boolean = source == query
    }

    class Group(private vararg val matchesQuery: BirthdayMatchesQuery) : BirthdayMatchesQuery {
        override fun matches(birthday: BirthdayDomain, queries: Collection<String>): Boolean =
            matchesQuery.any { it.matches(birthday, queries) }
    }
}