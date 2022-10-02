package ru.daniilglazkov.birthdays.domain.range

/**
 * @author Danil Glazkov on 17.09.2022, 02:59
 */
interface RangeTextFormat {
    fun <T : Comparable<T>> format(range: ClosedRange<T>): String

    abstract class Abstract : RangeTextFormat {
        protected abstract fun same(first: String): String
        protected abstract fun different(first: String, last: String): String

        override fun <T : Comparable<T>> format(range: ClosedRange<T>): String {
            return if (range.start.compareTo(range.endInclusive) == 0) {
                same(range.start.toString())
            } else {
                different(range.start.toString(), range.endInclusive.toString())
            }
        }
    }

    class Base : Abstract() {
        override fun same(first: String): String = first
        override fun different(first: String, last: String): String = "$first — $last"
    }
}