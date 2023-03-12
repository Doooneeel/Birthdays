package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomainSplitMapper

/**
 * @author Danil Glazkov on 18.08.2022, 19:10
 */
interface SplitBirthdayList {

    fun split(data: BirthdayListDomain): List<BirthdayListDomain>


    class Base(predicate: BirthdayListSplitPredicate<*>) : SplitBirthdayList {
        private val mapper = BirthdayListDomainSplitMapper.Base(predicate)

        override fun split(data: BirthdayListDomain): List<BirthdayListDomain> =
            data.map(mapper)
    }
}