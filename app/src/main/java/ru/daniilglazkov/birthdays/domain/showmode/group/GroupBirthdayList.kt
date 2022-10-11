package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.showmode.split.SplitBirthdayList
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
            return BirthdayListDomain.Empty.concatenate(split.split(data).map {
                it.addHeader(groupHeader)
            })
        }
    }
    class Unit : Group.Unit<BirthdayListDomain>(), GroupBirthdayList
}