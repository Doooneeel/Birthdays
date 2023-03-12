package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.domain.core.text.NormalizeQuery

/**
 * @author Danil Glazkov on 04.03.2024, 21:46
 */
interface BirthdaySearch {

    @Throws(NotFoundException::class)
    fun search(source: BirthdayListDomain, query: CharSequence): BirthdayListDomain


    class Base(
        private val matchesRequest: BirthdayMatchesQuery,
        private val normalizeQuery: NormalizeQuery
    ) : BirthdaySearch {
        override fun search(source: BirthdayListDomain, query: CharSequence): BirthdayListDomain {
            val normalizedQuery = normalizeQuery.normalize(query)

            val found = source.asList().filter { birthday: BirthdayDomain ->
                matchesRequest.matches(birthday, normalizedQuery)
            }
            return if (found.isEmpty())
                throw NotFoundException()
            else
                BirthdayListDomain.Base(found)
        }
    }
}