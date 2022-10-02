package ru.daniilglazkov.birthdays.domain.birthdays.showmode.group

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat

/**
 * @author Danil Glazkov on 23.09.2022, 03:26
 */
interface MakeHeader {
    fun make(list: List<BirthdayDomain>): BirthdayHeader

    class BasedOnFirst<T>(private val predicate: BirthdayGroupPredicate<T>) : MakeHeader {
        override fun make(list: List<BirthdayDomain>): BirthdayHeader = BirthdayHeader.Base(
            list.first().map(predicate).toString()
        )
    }

    class Range<T: Comparable<T>>(
        private val rangeTextFormat: RangeTextFormat,
        private val predicate: BirthdayGroupPredicate<T>
    ) : MakeHeader {
        override fun make(list: List<BirthdayDomain>): BirthdayHeader {
            val range = list.first().map(predicate)..list.last().map(predicate)
            return BirthdayHeader.Base(rangeTextFormat.format(range))
        }
    }
}