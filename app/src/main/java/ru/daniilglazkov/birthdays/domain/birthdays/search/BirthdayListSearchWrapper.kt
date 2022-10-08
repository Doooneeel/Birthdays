package ru.daniilglazkov.birthdays.domain.birthdays.search

import ru.daniilglazkov.birthdays.domain.birthdays.*
import java.time.LocalDate

/**
 * @author Danil Glazkov on 08.10.2022, 18:13
 */
interface BirthdayListSearchWrapper : BirthdayListDomain {
    fun search(query: CharSequence): BirthdayListDomain


    class Base(
        private val list: List<BirthdayDomain>,
        private val matchesQuery: BirthdayMatchesQuery,
        private val prepareQuery: PrepareQuery
    ) : BirthdayListDomain.Abstract(list),
        BirthdayListSearchWrapper
    {
        private inner class ItemMatchesQuery(private val query: String) : BirthdayDomain.CheckMapper {
            override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
                matchesQuery.matches(prepareQuery.prepare(query), prepareQuery.prepare(name), date)
        }

        override fun search(query: CharSequence): BirthdayListDomain {
            val matchesQuery = ItemMatchesQuery(query.toString())

            return BirthdayListDomain.Base(list.filter { birthday: BirthdayDomain ->
                birthday.map(matchesQuery)
            })
        }
    }
}