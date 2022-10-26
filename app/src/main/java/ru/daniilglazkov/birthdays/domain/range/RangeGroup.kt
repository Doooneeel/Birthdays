package ru.daniilglazkov.birthdays.domain.range

import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 17.09.2022, 02:42
 */
interface RangeGroup<R : RangeCategory<C>, C : Comparable<C>> {
    fun group(value: C): R

    abstract class Abstract<R : RangeCategory<C>, C : Comparable<C>> : RangeGroup<R, C> {
        protected abstract val ranges: List<R>

        override fun group(value: C): R =
            ranges.find { it.inRange(value) } ?: throw NotFoundException("Out of range: $value")
    }
}