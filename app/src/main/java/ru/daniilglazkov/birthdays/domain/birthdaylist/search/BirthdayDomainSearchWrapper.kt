package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 08.10.2022, 18:14
 */
interface BirthdayDomainSearchWrapper : BirthdayDomain {
    fun matches(query: String, birthdayMatchesQuery: BirthdayMatchesQuery): Boolean

    class Base(
        id: Int,
        type: BirthdayType,
        private val date: LocalDate,
        private val name: String
    ) : BirthdayDomain.Abstract(id, name, date, type),
        BirthdayDomainSearchWrapper
    {
        override fun matches(query: String, birthdayMatchesQuery: BirthdayMatchesQuery): Boolean {
            return birthdayMatchesQuery.matches(query, name, date)
        }
    }
}