package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain

/**
 * @author Danil Glazkov on 09.10.2022, 22:24
 */
interface BirthdayListSortMapper : BirthdayListDomain.Mapper<BirthdayListDomain> {

    class Ascending<C : Comparable<C>>(
        private val predicate: BirthdayDomain.Mapper<C>,
    ) : BirthdayListSortMapper {
        override fun map(list: List<BirthdayDomain>) =
            BirthdayListDomain.Base(list.sortedBy { it.map(predicate) })
    }

    class Descending<C : Comparable<C>>(
        private val predicate: BirthdayDomain.Mapper<C>,
    ) : BirthdayListSortMapper {
        override fun map(list: List<BirthdayDomain>) =
            BirthdayListDomain.Base(list.sortedBy { it.map(predicate) }.reversed())
    }

}