package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.Month

/**
 * @author Danil Glazkov on 18.08.2022, 14:33
 */
interface NextEvent {
    fun nextEvent(date: LocalDate) : LocalDate

    abstract class Abstract : NextEvent {
        protected fun isLeapDay(date: LocalDate) = date.isLeapYear && date.month == Month.FEBRUARY
                && date.dayOfMonth == 29
    }

    //Todo fix leap day
    class Base(private val now: LocalDate) : Abstract() {
        override fun nextEvent(date: LocalDate): LocalDate {
            var nextEvent = LocalDate.of(now.year, date.monthValue,
                if (isLeapDay(date)) date.plusDays(1).dayOfMonth
                else date.dayOfMonth
            )
            if (nextEvent.isBefore(now)) {
                nextEvent = nextEvent.plusYears(1)
            }
            return nextEvent
        }

    }


}