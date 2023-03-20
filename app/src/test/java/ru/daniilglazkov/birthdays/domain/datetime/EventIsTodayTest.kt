package ru.daniilglazkov.birthdays.domain.datetime

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class   EventIsTodayTest : BaseDateTest() {

    @Test
    fun test_now_leap_day() {
        val now = LocalDate.of(2020, Month.FEBRUARY, 29)
        val eventIsToday = EventIsToday.Base(now)

        runThroughAllDateVariations { date: LocalDate ->
            val isToday = date.monthValue == now.monthValue && date.dayOfMonth == now.dayOfMonth
            assertEquals(isToday, eventIsToday.isToday(date))
        }
    }

    @Test
    fun test_now_not_leap_day() {
        val now = LocalDate.of(2023, Month.FEBRUARY, 28)
        val eventIsToday = EventIsToday.Base(now)

        runThroughAllDateVariations { date: LocalDate ->
            val isLeapDay = date.monthValue == 2 && date.dayOfMonth == 29
            val isToday = date.monthValue == now.monthValue && date.dayOfMonth == now.dayOfMonth

            assertEquals(isToday || isLeapDay, eventIsToday.isToday(date))
        }
    }
}