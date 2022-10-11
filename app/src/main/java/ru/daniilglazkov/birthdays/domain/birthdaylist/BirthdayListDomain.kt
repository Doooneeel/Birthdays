package ru.daniilglazkov.birthdays.domain.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.Matches
import ru.daniilglazkov.birthdays.domain.core.Concatenate
import ru.daniilglazkov.birthdays.domain.core.IsEmpty

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListDomain : Matches<BirthdayListDomain>, IsEmpty, Concatenate<BirthdayListDomain> {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(private val list: List<BirthdayDomain>) : BirthdayListDomain {
        private val toMutableList = object : Mapper<MutableList<BirthdayDomain>> {
            override fun map(list: List<BirthdayDomain>) = list.toMutableList()
        }
        override fun concatenate(data: List<BirthdayListDomain>): BirthdayListDomain {
            val result = list.toMutableList()
            data.forEach { result.addAll(it.map(toMutableList)) }
            return Base(result)
        }
        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)

        override fun matches(data: BirthdayListDomain): Boolean {
            val birthdays = data.map(toMutableList)

            return if (list.size == birthdays.size) {
                birthdays.zip(list).all { it.first.matches(it.second) }
            } else false
        }
        override fun isEmpty(): Boolean = list.isEmpty()
    }

    class Base(list: List<BirthdayDomain>) : Abstract(list)
    object Empty : Abstract(emptyList())


    interface Mapper<T> {
        fun map(list: List<BirthdayDomain>): T

        abstract class AbstractSplit<P, R>(
            private val predicate: BirthdayDomain.Mapper<P>
        ) : Mapper<List<R>> {
            protected abstract fun split(list: List<BirthdayDomain>): R

            override fun map(list: List<BirthdayDomain>): List<R> {
                return list.groupBy { it.map(predicate) }.map { split(it.value) }
            }
        }
    }
}