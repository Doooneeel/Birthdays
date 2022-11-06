package ru.daniilglazkov.birthdays.domain.date

import java.time.LocalDate

/**
 * @author Danil Glazkov on 18.08.2022, 14:33
 */
interface NextEvent {
    fun nextEvent(date: LocalDate): LocalDate

    class Base(
        private val isLeapDay: IsLeapDay,
        private val eventIsToday: EventIsToday,
        private val handlePassedDate: HandlePassedDate,
        private val determineLeapDay: DetermineLeapDay,
        private val now: LocalDate
    ) : NextEvent {
        override fun nextEvent(date: LocalDate): LocalDate {
            return if (eventIsToday.isToday(date)) {
                now
            } else if (isLeapDay.isLeapDay(date)) {
                val year = now.year
                val nextEvent = LocalDate.of(year, date.month, determineLeapDay.leapDay(year))
                val nextYear = nextEvent.year + 1

                handlePassedDate.handle(nextEvent) { pastEvent: LocalDate ->
                    LocalDate.of(nextYear, pastEvent.month, determineLeapDay.leapDay(nextYear))
                }
            } else {
                handlePassedDate.handle(date.withYear(now.year)) { pastEvent: LocalDate ->
                    pastEvent.plusYears(1)
                }
            }
        }
    }
}