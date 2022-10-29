package ru.daniilglazkov.birthdays.domain.date

import java.time.Year

/**
 * @author Danil Glazkov on 29.10.2022, 23:40
 */
interface DetermineLeapDay {
    fun leapDay(year: Int): Int

    class Base : DetermineLeapDay {
        override fun leapDay(year: Int): Int =
            if (Year.isLeap(year.toLong())) 29 else 28
    }
}