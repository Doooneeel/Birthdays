package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat

/**
 * @author Danil Glazkov on 23.09.2022, 03:26
 */
interface MakeHeader {

    fun make(group: List<BirthdayDomain>): BirthdayDomain


    abstract class Abstract : MakeHeader {
        private var id: Int = 0

        protected abstract fun toText(group: List<BirthdayDomain>): String

        override fun make(group: List<BirthdayDomain>) = BirthdayDomain.Header(--id,
            if (group.isNotEmpty()) toText(group) else ""
        )
    }

    class BasedOnFirst(private val predicate: BirthdayGroupHeaderPredicate) : Abstract() {
        override fun toText(group: List<BirthdayDomain>): String = group.first().map(predicate)
    }

    class Edges(
        private val rangeTextFormat: RangeTextFormat,
        private val predicate: BirthdayGroupHeaderPredicate,
    ) : Abstract() {
        override fun toText(group: List<BirthdayDomain>): String = rangeTextFormat.format(
            group.first().map(predicate)..group.last().map(predicate)
        )
    }
}