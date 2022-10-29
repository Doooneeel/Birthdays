package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.Month

/**
 * @author Danil Glazkov on 26.10.2022, 07:03
 */
interface IsLeapDay {
    fun isLeapDay(date: LocalDate): Boolean

    class Base : IsLeapDay {
        override fun isLeapDay(date: LocalDate): Boolean =
            date.isLeapYear && date.month == Month.FEBRUARY && date.dayOfMonth == 29
    }
}