package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.Year

/**
 * @author Danil Glazkov on 29.10.2022, 23:40
 */
interface HandleLeapDay {
    fun handleLeapDay(date: LocalDate): Int

    class Base : HandleLeapDay {
        override fun handleLeapDay(date: LocalDate): Int =
            if (Year.isLeap(date.year.toLong())) 29 else 28
    }
}