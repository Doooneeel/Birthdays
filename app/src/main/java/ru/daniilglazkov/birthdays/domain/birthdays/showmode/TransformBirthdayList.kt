package ru.daniilglazkov.birthdays.domain.birthdays.showmode

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.group.GroupBirthdayList
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.sort.SortBirthdayList
import ru.daniilglazkov.birthdays.domain.core.Transform

/**
 * @author Danil Glazkov on 24.07.2022, 01:40
 */
interface TransformBirthdayList : Transform.Single<BirthdayListDomain> {

    abstract class Abstract(
        private val sort: SortBirthdayList,
        private val group: GroupBirthdayList
    ) : TransformBirthdayList {
        override fun transform(data: BirthdayListDomain): BirthdayListDomain =
            group.group(sort.sort(data))
    }

    class SortingAndGrouping(
        sort: SortBirthdayList,
        group: GroupBirthdayList
    ) : Abstract(sort, group)

    class Sorting(sort: SortBirthdayList) : Abstract(sort, GroupBirthdayList.Unit())

    class Grouping(group: GroupBirthdayList) : Abstract(SortBirthdayList.Unit(), group)
}