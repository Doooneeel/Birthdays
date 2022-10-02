package ru.daniilglazkov.birthdays.domain.range

/**
 * @author Danil Glazkov on 17.09.2022, 03:11
 */
interface RangeCategory<T : Comparable<T>> {
    fun isInRange(value: T): Boolean

    abstract class Abstract<T : Comparable<T>>(
        private vararg val ranges: ClosedRange<T>,
    ) : RangeCategory<T> {
        override fun isInRange(value: T): Boolean = ranges.any { range ->
            value in range
        }
    }
    abstract class AbstractInt(vararg ranges: IntRange) : Abstract<Int>(*ranges)
}

