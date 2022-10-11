package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat

/**
 * @author Danil Glazkov on 23.09.2022, 03:26
 */
interface MakeHeader {
    fun make(list: List<BirthdayDomain>): BirthdayDomain

    class BasedOnFirst<T>(private val predicate: BirthdayGroupPredicate<T>) : MakeHeader {
        override fun make(list: List<BirthdayDomain>): BirthdayDomain = BirthdayDomain.Header(
            list.first().map(predicate).toString()
        )
    }

    class Range<T: Comparable<T>>(
        private val rangeTextFormat: RangeTextFormat,
        private val predicate: BirthdayGroupPredicate<T>
    ) : MakeHeader {
        override fun make(list: List<BirthdayDomain>): BirthdayDomain {
            val range = list.first().map(predicate)..list.last().map(predicate)
            return BirthdayDomain.Header(rangeTextFormat.format(range))
        }
    }
}