package ru.daniilglazkov.birthdays.domain.birthdays.showmode.group

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain

/**
 * @author Danil Glazkov on 22.09.2022, 20:17
 */
interface BirthdayGroupDomain : BirthdayListDomain {
    fun addHeader(header: BirthdayHeader): BirthdayGroupDomain
    fun makeHeader(makeHeader: MakeHeader): BirthdayHeader

    class Base(
        private val list: List<BirthdayDomain>,
    ) : BirthdayListDomain.Abstract(list),
        BirthdayGroupDomain
    {
        override fun addHeader(header: BirthdayHeader): BirthdayGroupDomain {
            return Base(list.toMutableList().apply { add(0, header) })
        }
        override fun makeHeader(makeHeader: MakeHeader): BirthdayHeader = makeHeader.make(list)
    }

    interface Mapper<T> : BirthdayListDomain.Mapper<T> {

        class Split<T>(
            predicate: BirthdayDomain.Mapper<T>
        ) : BirthdayListDomain.Mapper.AbstractSplit<T, BirthdayGroupDomain>(predicate) {
            override fun split(list: List<BirthdayDomain>) = Base(list)
        }
    }
}