package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat

/**
 * @author Danil Glazkov on 23.09.2022, 03:26
 */
interface MakeHeader {
    fun make(list: List<BirthdayDomain>): BirthdayDomain

    abstract class Abstract : MakeHeader {
        protected abstract fun text(list: List<BirthdayDomain>): String
        private var id: Int = 0

        override fun make(list: List<BirthdayDomain>) =
            BirthdayDomain.Header(--id, text(list))
    }

    class BasedOnFirst<T>(private val predicate: BirthdayGroupHeaderPredicate<T>) : Abstract() {
        override fun text(list: List<BirthdayDomain>) = list.first().map(predicate).toString()
    }

    class Range<T: Comparable<T>>(
        private val rangeTextFormat: RangeTextFormat,
        private val predicate: BirthdayGroupHeaderPredicate<T>,
    ) : Abstract() {
        override fun text(list: List<BirthdayDomain>): String = rangeTextFormat.format(
            range = list.first().map(predicate)..list.last().map(predicate)
        )
    }
}