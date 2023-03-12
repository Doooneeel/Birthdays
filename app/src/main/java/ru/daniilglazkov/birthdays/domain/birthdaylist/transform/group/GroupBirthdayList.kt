package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split.SplitBirthdayList

/**
 * @author Danil Glazkov on 18.08.2022, 19:19
 */
interface GroupBirthdayList {

    fun group(source: BirthdayListDomain): BirthdayListDomain


    data class Base(
        private val split: SplitBirthdayList,
        private val makeHeader: MakeHeader,
    ) : GroupBirthdayList {
        override fun group(source: BirthdayListDomain): BirthdayListDomain {
            val groups: List<BirthdayListDomain> = split.split(source)

            return BirthdayListDomain.Base(buildList {
                groups.forEach { group: BirthdayListDomain ->
                    val groupAsList = group.asList()

                    add(makeHeader.make(groupAsList))
                    addAll(groupAsList)
                }
            })
        }
    }

    object Skip : GroupBirthdayList {
        override fun group(source: BirthdayListDomain) = source
    }
}