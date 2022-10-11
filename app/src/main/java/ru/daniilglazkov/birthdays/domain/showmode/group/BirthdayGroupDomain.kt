package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain

/**
 * @author Danil Glazkov on 22.09.2022, 20:17
 */
interface BirthdayGroupDomain : BirthdayListDomain {
    fun addHeader(header: BirthdayGroupHeader): BirthdayGroupDomain
    fun makeHeader(makeHeader: MakeHeader): BirthdayDomain

    class Base(
        private val list: List<BirthdayDomain>,
    ) : BirthdayListDomain.Abstract(list),
        BirthdayGroupDomain
    {
        override fun addHeader(header: BirthdayGroupHeader): BirthdayGroupDomain {
            return Base(list.toMutableList().also { resultList ->
                resultList.add(index = 0, header.header(group = this))
            })
        }
        override fun makeHeader(makeHeader: MakeHeader): BirthdayDomain =
            makeHeader.make(list)
    }

    interface Mapper<T> : BirthdayListDomain.Mapper<T> {

        class Split<T>(
            predicate: BirthdayDomain.Mapper<T>
        ) : BirthdayListDomain.Mapper.AbstractSplit<T, BirthdayGroupDomain>(predicate) {
            override fun split(list: List<BirthdayDomain>) = Base(list)
        }
    }
}