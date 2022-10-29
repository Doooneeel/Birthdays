package ru.daniilglazkov.birthdays.domain.date

import android.util.Log
import java.time.LocalDate
import java.time.Year

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
            return handlePassedDate.handle(date.withYear(now.year)) { result: LocalDate ->
                result.plusYears(1)
            }
        }
    }

    class Leap(
        private val handlePassedDate: HandlePassedDate,
        private val handleLeapDay: HandleLeapDay,
        private val now: LocalDate,
    ) : HandleDate {
        override fun handleDate(date: LocalDate): LocalDate {
            val nextEvent = LocalDate.of(now.year, date.monthValue, handleLeapDay.handleLeapDay(now))
            return handlePassedDate.handle(nextEvent) {
                LocalDate.of(it.year + 1, it.month, handleLeapDay.handleLeapDay(it))
            }
        }
    }
}