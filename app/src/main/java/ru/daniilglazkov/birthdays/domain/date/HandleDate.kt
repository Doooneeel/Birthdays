package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate

/**
 * @author Danil Glazkov on 29.10.2022, 05:33
 */
interface HandleDate {
    fun handleDate(date: LocalDate): LocalDate

    class Base(
        private val handlePassedDate: HandlePassedDate,
        private val now: LocalDate,
    ) : HandleDate {
        override fun handleDate(date: LocalDate): LocalDate {
            return handlePassedDate.handle(date.withYear(now.year)) { pastEvent: LocalDate ->
                pastEvent.plusYears(1)
            }
        }
    }

    class Leap(
        private val handlePassedDate: HandlePassedDate,
        private val determineLeapDay: DetermineLeapDay,
        private val now: LocalDate,
    ) : HandleDate {
        override fun handleDate(date: LocalDate): LocalDate {
            val year = now.year
            val nextEvent = LocalDate.of(year, date.monthValue, determineLeapDay.leapDay(year))

            return handlePassedDate.handle(nextEvent) { pastEvent: LocalDate ->
                val nextYear = pastEvent.year + 1
                LocalDate.of(nextYear, pastEvent.month, determineLeapDay.leapDay(nextYear))
            }
        }
    }
}