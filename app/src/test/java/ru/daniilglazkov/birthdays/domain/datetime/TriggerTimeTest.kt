package ru.daniilglazkov.birthdays.domain.datetime

import org.junit.Assert.*
import org.junit.Test
import java.time.*

/**
 * @author Danil Glazkov on 18.03.2023, 19:54
 */
class TriggerTimeTest {

    private val currentDate = LocalDate.of(2023, 3, 15)
    private val nextDay = LocalDate.of(2023, 3, 16)
    private val zoneOffset = ZoneOffset.UTC

    @Test
    fun test_morning_before_now() {
        val expected = LocalDateTime.of(currentDate, LocalTime.of(9, 0))

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(9, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(7, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(6, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(4, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(1, 0)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(1, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(1, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(2, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(3, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(3, 45)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 1)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 45)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 49)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 58)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(8, 59)), expected)
    }

    @Test
    fun test_morning_after_now() {
        val expected = LocalDateTime.of(nextDay, LocalTime.of(9, 0))

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(9, 1)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(9, 10)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(9, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(9, 45)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(10, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(10, 20)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(10, 59)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(12, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(15, 45)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(20, 20)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(20, 45)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(23, 59)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(23, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(23, 0)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(20, 30)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(19, 10)), expected)

        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(21, 59)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(21, 0)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(21, 45)), expected)
        assertTriggerTimeEquals(LocalDateTime.of(currentDate, LocalTime.of(21, 30)), expected)
    }


    private fun assertTriggerTimeEquals(now: LocalDateTime, expected: LocalDateTime) {
        val triggerTime = TriggerTime.Morning(zoneOffset)

        val actualInMillis = triggerTime.time(now.toInstant(zoneOffset).toEpochMilli())
        val expectedInMillis = expected.toInstant(zoneOffset).toEpochMilli()

        val comment = "actual = ${ 
            LocalDateTime.ofInstant(Instant.ofEpochMilli(actualInMillis), zoneOffset)
        }\nexpected = ${
            LocalDateTime.ofInstant(Instant.ofEpochMilli(expectedInMillis), zoneOffset)
        }"

        assertEquals(comment, expectedInMillis, actualInMillis)
    }
}