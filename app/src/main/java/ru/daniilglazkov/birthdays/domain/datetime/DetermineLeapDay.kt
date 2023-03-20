package ru.daniilglazkov.birthdays.domain.datetime

import java.time.Month
import java.time.YearMonth

/**
 * @author Danil Glazkov on 29.10.2022, 23:40
 */
interface DetermineLeapDay {

    fun leapDay(year: Int): Int


    class Base : DetermineLeapDay {
        override fun leapDay(year: Int): Int = YearMonth.of(year, Month.FEBRUARY)
            .lengthOfMonth()
    }
}