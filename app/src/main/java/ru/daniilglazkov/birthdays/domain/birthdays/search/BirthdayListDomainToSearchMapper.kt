package ru.daniilglazkov.birthdays.domain.birthdays.search

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain

/**
 * @author Danil Glazkov on 08.10.2022, 18:14
 */
interface BirthdayListDomainToSearchMapper : BirthdayListDomain.Mapper<BirthdayListSearchWrapper> {

    class Base(
        private val birthdayMatchesQuery: BirthdayMatchesQuery,
        private val itemPredicate: BirthdayDomain.CheckMapper,
        private val prepareQuery: PrepareQuery,
    ) : BirthdayListDomainToSearchMapper {

        override fun map(list: List<BirthdayDomain>): BirthdayListSearchWrapper {
            val filteredList = list.filter { it.map(itemPredicate) }
            return BirthdayListSearchWrapper.Base(filteredList, birthdayMatchesQuery, prepareQuery)
        }
    }
}
