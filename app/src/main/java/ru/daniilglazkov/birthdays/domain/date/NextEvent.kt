package ru.daniilglazkov.birthdays.domain.date

import android.util.Log
import java.time.LocalDate

/**
 * @author Danil Glazkov on 18.08.2022, 14:33
 */
interface NextEvent {
    fun nextEvent(date: LocalDate): LocalDate

    class Base(
        private val isLeapDay: IsLeapDay,
        private val handleLeapDay: HandleDate,
        private val handleDate: HandleDate,
        private val now: LocalDate
    ) : NextEvent {
        override fun nextEvent(date: LocalDate): LocalDate {
            if (date.dayOfMonth == now.dayOfMonth && date.monthValue == now.monthValue) {
                return now
            }
            val handleDate = if (isLeapDay.isLeapDay(date)) handleLeapDay else handleDate
            return handleDate.handleDate(date)
        }
    }
}