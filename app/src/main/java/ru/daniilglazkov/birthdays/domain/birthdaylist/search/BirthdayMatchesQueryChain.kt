package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import java.time.LocalDate

/**
 * @author Danil Glazkov on 10.08.2022, 17:21
 */
class BirthdayMatchesQueryChain(
    private val base: BirthdayMatchesQuery,
    private val next: BirthdayMatchesQuery
) : BirthdayMatchesQuery {
    override fun matches(query: String, name: String, date: LocalDate): Boolean =
        base.matches(query, name, date) || next.matches(query, name, date)
}