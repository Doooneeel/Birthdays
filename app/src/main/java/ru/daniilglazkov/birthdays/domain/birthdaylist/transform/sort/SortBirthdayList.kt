package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListSortMapper
import ru.daniilglazkov.birthdays.domain.core.Sort

/**
 * @author Danil Glazkov on 18.08.2022, 19:04
 */
interface SortBirthdayList : Sort<BirthdayListDomain> {

    abstract class Abstract(
        private val sortMapper: BirthdayListDomain.Mapper<BirthdayListDomain>
    ) : SortBirthdayList {
        override fun sort(data: BirthdayListDomain): BirthdayListDomain = data.map(sortMapper)
    }

    class Ascending<T : Comparable<T>>(predicate: BirthdayDomain.Mapper<T>) : Abstract(
        BirthdayListSortMapper.Ascending(predicate)
    )

    class Descending<T : Comparable<T>>(predicate: BirthdayDomain.Mapper<T>) : Abstract(
        BirthdayListSortMapper.Descending(predicate)
    )

    class Base(sortMapper: BirthdayListDomain.Mapper<BirthdayListDomain>) : Abstract(sortMapper)
}