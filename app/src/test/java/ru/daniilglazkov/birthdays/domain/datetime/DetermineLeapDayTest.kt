package ru.daniilglazkov.birthdays.domain.datetime

import org.junit.Assert.assertEquals
import org.junit.Test

class DetermineLeapDayTest {

    @Test
    fun test_calculate_leap_day() {
        val determineLeapDay = DetermineLeapDay.Base()

        assertEquals(29, determineLeapDay.leapDay(2020))
        assertEquals(29, determineLeapDay.leapDay(2016))
        assertEquals(29, determineLeapDay.leapDay(2000))
        assertEquals(29, determineLeapDay.leapDay(1904))
        assertEquals(29, determineLeapDay.leapDay(2004))

        assertEquals(28, determineLeapDay.leapDay(2401))
        assertEquals(28, determineLeapDay.leapDay(1900))
        assertEquals(28, determineLeapDay.leapDay(2021))
        assertEquals(28, determineLeapDay.leapDay(2022))
        assertEquals(28, determineLeapDay.leapDay(2023))
        assertEquals(28, determineLeapDay.leapDay(2001))
    }
}