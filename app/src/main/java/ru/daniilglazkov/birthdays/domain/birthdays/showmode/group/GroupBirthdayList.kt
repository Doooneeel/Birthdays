package ru.daniilglazkov.birthdays.domain.birthdays.showmode.group

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.split.SplitBirthdayList
import ru.daniilglazkov.birthdays.domain.core.Group

/**
 * @author Danil Glazkov on 18.08.2022, 19:19
 */
interface GroupBirthdayList : Group<BirthdayListDomain> {

    class Base(
        private val split: SplitBirthdayList,
        private val groupHeader: BirthdayGroupHeader,
    ) : GroupBirthdayList {
        override fun group(data: BirthdayListDomain): BirthdayListDomain {
            val result = BirthdayListDomain.Empty()
            return result.concatenate(split.split(data).map { group: BirthdayGroupDomain ->
                group.addHeader(groupHeader.header(group))
            })
        }
    }
    class Unit : Group.Unit<BirthdayListDomain>(), GroupBirthdayList
}