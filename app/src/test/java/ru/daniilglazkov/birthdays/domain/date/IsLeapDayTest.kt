package ru.daniilglazkov.birthdays.domain.date

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

class IsLeapDayTest {

    @Test
    fun test_is_leap_day() {
        val isLeapDay = IsLeapDay.Base()

        assertTrue(isLeapDay.isLeapDay(LocalDate.of(2020, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(2020, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(2004, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(2016, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(1920, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(2400, 2, 29)))
        assertTrue(isLeapDay.isLeapDay(LocalDate.of(1956, 2, 29)))


        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2401, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2015, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(1955, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2021, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(1900, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2021, 1, 29)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 1, 29)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2016, 2, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 3, 29)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 3, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(1900, 3, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 12, 28)))
        assertFalse(isLeapDay.isLeapDay(LocalDate.of(2020, 12, 29)))
    }

}