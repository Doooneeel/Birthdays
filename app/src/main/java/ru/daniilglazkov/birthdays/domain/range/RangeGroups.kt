package ru.daniilglazkov.birthdays.domain.range

import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 17.09.2022, 02:42
 */
interface RangeGroups<R : Range<C>, C : Comparable<C>> {

    fun group(value: C): R


    abstract class Abstract<R : Range<C>, C : Comparable<C>>(
        private val ranges: List<R>
    ) : RangeGroups<R, C> {
        override fun group(value: C): R = ranges.find { it.contains(value) }
            ?: throw NotFoundException("Out of range: $value")
    }

    data class Base<R : Range<C>, C : Comparable<C>>(
        private val ranges: List<R>,
    ) : Abstract<R, C>(ranges)
}