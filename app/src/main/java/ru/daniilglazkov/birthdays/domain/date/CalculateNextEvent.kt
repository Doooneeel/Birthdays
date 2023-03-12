package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate
import java.time.Month.*

/**
 * @author Danil Glazkov on 18.08.2022, 14:33
 */
interface CalculateNextEvent {

    fun nextEvent(date: LocalDate): LocalDate

    
    class Base(
        private val eventIsToday: EventIsToday,
        private val isLeapDay: IsLeapDay,
        determineLeapDay: DetermineLeapDay,
        private val currentDate: LocalDate
    ) : CalculateNextEvent {
        private val currentYear: Int = currentDate.year
        private val nextYear: Int = currentYear + 1

        private val leapDayInCurrentYear: Int by lazy { determineLeapDay.leapDay(currentYear) }
        private val leapDayInNextYear: Int by lazy { determineLeapDay.leapDay(nextYear) }

        override fun nextEvent(date: LocalDate): LocalDate {
            if (eventIsToday.isToday(date)) {
                return currentDate
            }
            val nextEventInCurrentYear: LocalDate
            val nextEventInNextYear: LocalDate

            if (isLeapDay.isLeapDay(date)) {
                nextEventInCurrentYear = LocalDate.of(currentYear, FEBRUARY, leapDayInCurrentYear)
                nextEventInNextYear = LocalDate.of(nextYear, FEBRUARY, leapDayInNextYear)
            } else {
                nextEventInCurrentYear = date.withYear(currentYear)
                nextEventInNextYear = nextEventInCurrentYear.plusYears(1)
            }

            return if (nextEventInCurrentYear < currentDate) {
                nextEventInNextYear
            } else
                nextEventInCurrentYear
        }
    }
}