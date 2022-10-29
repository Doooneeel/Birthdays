package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate

/**
 * @author Danil Glazkov on 25.10.2022, 06:11
 */
interface EventIsToday {
    fun isToday(date: LocalDate): Boolean
    
    class Base(private val now: LocalDate) : EventIsToday {
        override fun isToday(date: LocalDate): Boolean =
            date.withYear(now.year) == now
    }
}