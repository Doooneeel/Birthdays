package ru.daniilglazkov.birthdays.domain.datetime

import java.time.LocalDate

/**
 * @author Danil Glazkov on 26.10.2022, 07:03
 */
interface IsLeapDay {

    fun isLeapDay(date: LocalDate): Boolean


    class Base : IsLeapDay {
        override fun isLeapDay(date: LocalDate): Boolean =
            date.dayOfMonth == 29 && date.monthValue == 2
    }
}