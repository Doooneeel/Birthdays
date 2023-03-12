package ru.daniilglazkov.birthdays.domain.range

import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter

/**
 * @author Danil Glazkov on 17.09.2022, 02:59
 */
interface RangeTextFormat {

    fun <T : Comparable<T>> format(range: ClosedRange<T>): String


    class Base(private val delimiter: AddDelimiter) : RangeTextFormat {
        override fun <T : Comparable<T>> format(range: ClosedRange<T>): String = delimiter.add(
            range.start.toString(),
            range.endInclusive.toString()
        )
    }
}