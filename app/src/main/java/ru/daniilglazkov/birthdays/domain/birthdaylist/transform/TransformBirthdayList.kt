package ru.daniilglazkov.birthdays.domain.birthdaylist.transform

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortBirthdayList

/**
 * @author Danil Glazkov on 24.07.2022, 01:40
 */
interface TransformBirthdayList {

    fun transform(source: BirthdayListDomain): BirthdayListDomain


    data class Base(
        private val sort: SortBirthdayList,
        private val group: GroupBirthdayList
    ) : TransformBirthdayList {
        override fun transform(source: BirthdayListDomain): BirthdayListDomain {
            val sortedList: BirthdayListDomain = sort.sort(source)
            return group.group(sortedList)
        }
    }
}