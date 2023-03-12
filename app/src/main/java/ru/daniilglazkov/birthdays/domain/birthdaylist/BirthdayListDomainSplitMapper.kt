package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain

/**
 * @author Danil Glazkov on 13.02.2023, 18:04
 */
interface BirthdayListDomainSplitMapper<T> : BirthdayListDomain.Mapper<List<T>> {

    abstract class Abstract<T>(
        private val predicate: BirthdayDomain.Mapper<*>
    ) : BirthdayListDomainSplitMapper<T> {

        protected abstract fun split(group: List<BirthdayDomain>): T

        override fun map(list: List<BirthdayDomain>): List<T> =
            list.groupBy { it.map(predicate) }.map { split(it.value) }
    }

    class Base(predicate: BirthdayDomain.Mapper<*>) : Abstract<BirthdayListDomain>(predicate) {
        override fun split(group: List<BirthdayDomain>) = BirthdayListDomain.Base(group)
    }
}