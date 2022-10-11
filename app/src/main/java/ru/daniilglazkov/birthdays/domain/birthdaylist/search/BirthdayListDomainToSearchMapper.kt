package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 08.10.2022, 18:14
 */
interface BirthdayListDomainToSearchMapper : BirthdayListDomain.Mapper<BirthdayListSearchWrapper> {

    class Base(
        private val birthdayMatchesQuery: BirthdayMatchesQuery,
        private val itemPredicate: BirthdayCheckMapper,
        private val prepareQuery: PrepareQuery,
    ) : BirthdayListDomainToSearchMapper {

        override fun map(list: List<BirthdayDomain>): BirthdayListSearchWrapper {
            val filteredList = list.filter { it.map(itemPredicate) }
            return BirthdayListSearchWrapper.Base(filteredList, birthdayMatchesQuery, prepareQuery)
        }
    }
}
