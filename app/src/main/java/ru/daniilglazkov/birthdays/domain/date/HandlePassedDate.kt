package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate

/**
 * @author Danil Glazkov on 29.10.2022, 05:36
 */
interface HandlePassedDate {
    fun handle(source: LocalDate, newDate: (LocalDate) -> LocalDate): LocalDate

    class Base(private val now: LocalDate) : HandlePassedDate {
        override fun handle(source: LocalDate, newDate: (LocalDate) -> LocalDate): LocalDate {
            return if (source.isBefore(now)) newDate.invoke(source) else source
        }
    }
}