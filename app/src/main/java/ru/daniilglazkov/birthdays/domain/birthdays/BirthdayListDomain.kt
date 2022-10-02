package ru.daniilglazkov.birthdays.domain.birthdays

import ru.daniilglazkov.birthdays.core.IsEmpty
import ru.daniilglazkov.birthdays.core.Matches
import ru.daniilglazkov.birthdays.domain.core.Concatenate

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
interface BirthdayListDomain : Matches<BirthdayListDomain>, IsEmpty, Concatenate<BirthdayListDomain> {
    fun <T> map(mapper: Mapper<T>): T

    abstract class Abstract(private val list: List<BirthdayDomain>) : BirthdayListDomain {
        private val compareList by lazy { Mapper.CompareList(list) }

        private val toMutableList = object : Mapper<MutableList<BirthdayDomain>> {
            override fun map(list: List<BirthdayDomain>) = list.toMutableList()
        }
        override fun concatenate(data: List<BirthdayListDomain>): BirthdayListDomain {
            val result = list.toMutableList()
            data.forEach { result.addAll(it.map(toMutableList)) }
            return Base(result)
        }

        override fun <T> map(mapper: Mapper<T>): T = mapper.map(list)
        override fun matches(data: BirthdayListDomain): Boolean = data.map(compareList)
        override fun isEmpty(): Boolean = list.isEmpty()
    }

    class Base(list: List<BirthdayDomain>) : Abstract(list)
    class Empty : Abstract(emptyList())


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

        class CompareList(private val birthdays: List<BirthdayDomain>): Mapper<Boolean> {
            override fun map(list: List<BirthdayDomain>): Boolean {
                return if (list.size == birthdays.size) {
                    birthdays.zip(list).all { it.first.matches(it.second) }
                } else false
            }
        }
    }

    interface CountMapper : Mapper<Int> {
        abstract class Abstract(private val predicate: BirthdayDomain.CheckMapper) : CountMapper {
            override fun map(list: List<BirthdayDomain>): Int = list.count { it.map(predicate) }
        }
        class CountWithoutHeaders : Abstract(BirthdayDomain.CheckMapper.IsNotHeader())

        class CountHeaders : Abstract(BirthdayDomain.CheckMapper.IsHeader())
    }

    interface SortMapper : Mapper<BirthdayListDomain> {

        class Ascending<C : Comparable<C>>(
            private val predicate: BirthdayDomain.Mapper<C>,
        ) : SortMapper {
            override fun map(list: List<BirthdayDomain>) =
                Base(list.sortedBy { it.map(predicate) })
        }
        class Descending<C : Comparable<C>>(
            private val predicate: BirthdayDomain.Mapper<C>,
        ) : SortMapper {
            override fun map(list: List<BirthdayDomain>) =
                Base(list.sortedBy { it.map(predicate) }.reversed())
        }
    }
}