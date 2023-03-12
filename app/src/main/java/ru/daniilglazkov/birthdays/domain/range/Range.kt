package ru.daniilglazkov.birthdays.domain.range

/**
 * @author Danil Glazkov on 17.09.2022, 03:11
 */
interface Range<T : Comparable<T>> {

    fun contains(value: T): Boolean


    abstract class Abstract<T : Comparable<T>>(
        private val ranges: List<ClosedRange<T>>,
    ) : Range<T> {
        constructor(vararg ranges: ClosedRange<T>): this(ranges.toList())

        override fun contains(value: T): Boolean = ranges.any { range ->
            value in range
        }
    }

    abstract class Integer(vararg ranges: IntRange) : Abstract<Int>(*ranges)


    data class Base<T : Comparable<T>>(
        private val ranges: List<ClosedRange<T>>,
    ) : Abstract<T>(ranges) {
        constructor(vararg ranges: ClosedRange<T>): this(ranges.toList())
    }

}

