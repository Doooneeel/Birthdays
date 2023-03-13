package ru.daniilglazkov.birthdays.domain.date

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.time.*

/**
 * @author Danil Glazkov on 02.02.2023, 1:01
 */
class CalculateNextEventTest : BaseDateTest() {

    private val now = LocalDate.of(2020, 1, 1)

    private lateinit var eventIsToday: TestEventIsToday
    private lateinit var isLeapDay: TestIsLeapDay
    private lateinit var calculateLeapDay: TestDetermineLeapDay
    private lateinit var testNextEvent: CalculateNextEvent

    private fun makeNextEvent(now: LocalDate) = CalculateNextEvent.Base(
        EventIsToday.Base(now),
        IsLeapDay.Base(),
        DetermineLeapDay.Base(),
        now
    )

    @Before
    fun setUp() {
        eventIsToday = TestEventIsToday()
        isLeapDay = TestIsLeapDay()
        calculateLeapDay = TestDetermineLeapDay()

        testNextEvent = CalculateNextEvent.Base(
            eventIsToday,
            isLeapDay,
            calculateLeapDay,
            now
        )
    }

    @Test
    fun test_event_today() {
        eventIsToday.result = true
        isLeapDay.result = false

        assertEquals(now, testNextEvent.nextEvent(LocalDate.MAX))
        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(LocalDate.MAX, eventIsToday.calledList[0])
        assertEquals(0, isLeapDay.count)
        assertEquals(0, calculateLeapDay.calledList.size)

        runThroughAllDateVariations { now: LocalDate ->
            val nextEvent = makeNextEvent(now)
            assertEquals(now, nextEvent.nextEvent(now))
        }
    }

    @Test
    fun test_is_leap_day() {
        eventIsToday.result = false
        isLeapDay.result = true
        calculateLeapDay.result = 10

        val date = LocalDate.of(2002, 1, 1)

        testNextEvent.nextEvent(date)

        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(date, eventIsToday.calledList[0])

        assertEquals(1, isLeapDay.count)
        assertEquals(2, calculateLeapDay.calledList.size)

        testNextEvent.nextEvent(date)

        assertEquals(2, eventIsToday.calledList.size)
        assertEquals(2, isLeapDay.count)

        assertEquals(2, calculateLeapDay.calledList.size)
        assertEquals(now.year, calculateLeapDay.calledList[0])
        assertEquals(now.year + 1, calculateLeapDay.calledList[1])
    }

    @Test
    fun test_not_leap_day() {
        eventIsToday.result = false
        isLeapDay.result = false

        testNextEvent.nextEvent(LocalDate.MIN)

        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(LocalDate.MIN, eventIsToday.calledList[0])

        assertEquals(1, isLeapDay.count)

        testNextEvent.nextEvent(LocalDate.MIN)

        assertEquals(2, eventIsToday.calledList.size)
        assertEquals(2, isLeapDay.count)

        assertEquals(0, calculateLeapDay.calledList.size)
    }

    @Test
    fun test_leap_date() {
        val assertEqualsLeapDate = { nextEvent: CalculateNextEvent, expected: LocalDate ->
            runThroughAllDateVariations { date: LocalDate ->
                if (date.dayOfMonth == 29 && date.monthValue == 2) {
                    assertEquals(expected, nextEvent.nextEvent(date))
                }
            }
        }

        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2020, 1, 10)), LocalDate.of(2020, 2, 29))
        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2020, 3, 10)), LocalDate.of(2021, 2, 28))
        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2020, 2, 29)), LocalDate.of(2020, 2, 29))

        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2021, 1, 10)), LocalDate.of(2021, 2, 28))
        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2021, 3, 10)), LocalDate.of(2022, 2, 28))
        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2021, 2, 28)), LocalDate.of(2021, 2, 28))

        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2023, 1, 10)), LocalDate.of(2023, 2, 28))
        assertEqualsLeapDate(makeNextEvent(LocalDate.of(2023, 3, 10)), LocalDate.of(2024, 2, 29))
    }

    @Test
    fun test_after_leap_day_not_leap_year() {
        val now = LocalDate.of(2022, 10, 10)

        assertEventBeforeNow(event = LocalDate.of(2022, 10, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 10, 9), now)

        assertEventBeforeNow(event = LocalDate.of(2018, 3, 30), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 2, 22), now)
        assertEventBeforeNow(event = LocalDate.of(2013, 6, 14), now)
        assertEventBeforeNow(event = LocalDate.of(2004, 9, 4), now)

        assertEventBeforeNow(event = LocalDate.of(2120, 10, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2300, 1, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2330, 8, 10), now)
        assertEventBeforeNow(event = LocalDate.of(3000, 3, 30), now)


        assertEventAfterNow(event = LocalDate.of(2022, 10, 10), now)
        assertEventAfterNow(event = LocalDate.of(2022, 10, 11), now)
        assertEventAfterNow(event = LocalDate.of(2000, 10, 10), now)
        assertEventAfterNow(event = LocalDate.of(2000, 10, 11), now)

        assertEventAfterNow(event = LocalDate.of(2012, 10, 20), now)
        assertEventAfterNow(event = LocalDate.of(2012, 12, 31), now)
        assertEventAfterNow(event = LocalDate.of(2020, 11, 17), now)
        assertEventAfterNow(event = LocalDate.of(2005, 11, 4), now)

        assertEventAfterNow(event = LocalDate.of(2030, 10, 20), now)
        assertEventAfterNow(event = LocalDate.of(2100, 11, 1), now)
        assertEventAfterNow(event = LocalDate.of(2333, 12, 12), now)
        assertEventAfterNow(event = LocalDate.of(3000, 10, 30), now)
    }

    @Test
    fun test_before_leap_day_not_leap_year() {
        val now = LocalDate.of(2022, 2, 10)

        assertEventBeforeNow(event = LocalDate.of(2022, 2, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 2, 9), now)

        assertEventBeforeNow(event = LocalDate.of(2016, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2011, 2, 7), now)
        assertEventBeforeNow(event = LocalDate.of(2010, 1, 16), now)
        assertEventBeforeNow(event = LocalDate.of(2008, 1, 28), now)

        assertEventBeforeNow(event = LocalDate.of(3000, 1, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2200, 2, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2150, 2, 4), now)
        assertEventBeforeNow(event = LocalDate.of(2188, 1, 30), now)


        assertEventAfterNow(event = LocalDate.of(2022, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(2022, 2, 11), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 11), now)

        assertEventAfterNow(event = LocalDate.of(2019, 2, 28), now)
        assertEventAfterNow(event = LocalDate.of(2019, 12, 31), now)
        assertEventAfterNow(event = LocalDate.of(2013, 8, 1), now)
        assertEventAfterNow(event = LocalDate.of(2002, 5, 11), now)

        assertEventAfterNow(event = LocalDate.of(3010, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(2300, 4, 11), now)
        assertEventAfterNow(event = LocalDate.of(3100, 12, 29), now)
        assertEventAfterNow(event = LocalDate.of(2331, 8, 5), now)
    }

    @Test
    fun test_after_leap_day_leap_year() {
        val now = LocalDate.of(2020, 5, 15)

        assertEventBeforeNow(event = LocalDate.of(2020, 5, 14), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 5, 14), now)

        assertEventBeforeNow(event = LocalDate.of(2017, 4, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2012, 2, 28), now)
        assertEventBeforeNow(event = LocalDate.of(2003, 3, 11), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 1, 17), now)

        assertEventBeforeNow(event = LocalDate.of(3000, 5, 14), now)
        assertEventBeforeNow(event = LocalDate.of(3200, 3, 11), now)
        assertEventBeforeNow(event = LocalDate.of(2314, 4, 8), now)
        assertEventBeforeNow(event = LocalDate.of(2754, 2, 12), now)


        assertEventAfterNow(event = LocalDate.of(2020, 5, 15), now)
        assertEventAfterNow(event = LocalDate.of(2020, 5, 16), now)
        assertEventAfterNow(event = LocalDate.of(2000, 5, 15), now)
        assertEventAfterNow(event = LocalDate.of(2000, 5, 16), now)

        assertEventAfterNow(event = LocalDate.of(2000, 5, 16), now)
        assertEventAfterNow(event = LocalDate.of(2006, 8, 2), now)
        assertEventAfterNow(event = LocalDate.of(2017, 6, 30), now)
        assertEventAfterNow(event = LocalDate.of(2012, 11, 30), now)

        assertEventAfterNow(event = LocalDate.of(3227, 5, 15), now)
        assertEventAfterNow(event = LocalDate.of(3000, 12, 22), now)
        assertEventAfterNow(event = LocalDate.of(2200, 6, 1), now)
        assertEventAfterNow(event = LocalDate.of(2150, 7, 23), now)
    }

    @Test
    fun test_before_leap_day_leap_year() {
        val now = LocalDate.of(2020, 2, 5)

        assertEventBeforeNow(event = LocalDate.of(2020, 2, 4), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 2, 4), now)

        assertEventBeforeNow(event = LocalDate.of(2018, 1, 6), now)
        assertEventBeforeNow(event = LocalDate.of(2020, 1, 14), now)
        assertEventBeforeNow(event = LocalDate.of(2020, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2013, 2, 4), now)

        assertEventBeforeNow(event = LocalDate.of(3000, 2, 4), now)
        assertEventBeforeNow(event = LocalDate.of(2033, 1, 31), now)
        assertEventBeforeNow(event = LocalDate.of(2903, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2333, 1, 8), now)


        assertEventAfterNow(event = LocalDate.of(2020, 2, 5), now)
        assertEventAfterNow(event = LocalDate.of(2020, 2, 6), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 5), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 6), now)

        assertEventAfterNow(event = LocalDate.of(2020, 2, 11), now)
        assertEventAfterNow(event = LocalDate.of(2019, 12, 31), now)
        assertEventAfterNow(event = LocalDate.of(2015, 8, 17), now)
        assertEventAfterNow(event = LocalDate.of(2014, 5, 9), now)

        assertEventAfterNow(event = LocalDate.of(2122, 2, 5), now)
        assertEventAfterNow(event = LocalDate.of(3000, 10, 12), now)
        assertEventAfterNow(event = LocalDate.of(2987, 12, 4), now)
        assertEventAfterNow(event = LocalDate.of(2415, 3, 1), now)
    }

    @Test
    fun test_after_leap_day_next_year_leap() {
        val now = LocalDate.of(2023, 5, 10)

        assertEventBeforeNow(event = LocalDate.of(2023, 5, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 5, 9), now)

        assertEventBeforeNow(event = LocalDate.of(2012, 2, 28), now)
        assertEventBeforeNow(event = LocalDate.of(2012, 2, 28), now)
        assertEventBeforeNow(event = LocalDate.of(2012, 5, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2020, 5, 9), now)

        assertEventBeforeNow(event = LocalDate.of(3002, 5, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2304, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2555, 1, 31), now)
        assertEventBeforeNow(event = LocalDate.of(2865, 3, 4), now)


        assertEventAfterNow(event = LocalDate.of(2023, 5, 10), now)
        assertEventAfterNow(event = LocalDate.of(2023, 5, 11), now)
        assertEventAfterNow(event = LocalDate.of(2000, 5, 10), now)
        assertEventAfterNow(event = LocalDate.of(2000, 5, 11), now)

        assertEventAfterNow(event = LocalDate.of(2012, 5, 10), now)
        assertEventAfterNow(event = LocalDate.of(2012, 5, 11), now)
        assertEventAfterNow(event = LocalDate.of(2012, 6, 1), now)
        assertEventAfterNow(event = LocalDate.of(2004, 12, 31), now)

        assertEventAfterNow(event = LocalDate.of(3052, 5, 10), now)
        assertEventAfterNow(event = LocalDate.of(2404, 8, 7), now)
        assertEventAfterNow(event = LocalDate.of(2535, 11, 30), now)
        assertEventAfterNow(event = LocalDate.of(2865, 6, 1), now)
    }

    @Test
    fun test_before_leap_day_next_year_leap() {
        val now = LocalDate.of(2023, 2, 10)

        assertEventBeforeNow(event = LocalDate.of(2023, 2, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 2, 9), now)

        assertEventBeforeNow(event = LocalDate.of(2023, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2000, 2, 1), now)
        assertEventBeforeNow(event = LocalDate.of(2007, 1, 31), now)
        assertEventBeforeNow(event = LocalDate.of(2003, 2, 5), now)

        assertEventBeforeNow(event = LocalDate.of(4321, 2, 9), now)
        assertEventBeforeNow(event = LocalDate.of(2999, 1, 31), now)
        assertEventBeforeNow(event = LocalDate.of(3753, 1, 2), now)
        assertEventBeforeNow(event = LocalDate.of(2154, 2, 3), now)


        assertEventAfterNow(event = LocalDate.of(2023, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(2023, 2, 11), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(2000, 2, 11), now)

        assertEventAfterNow(event = LocalDate.of(2017, 2, 11), now)
        assertEventAfterNow(event = LocalDate.of(2017, 3, 1), now)
        assertEventAfterNow(event = LocalDate.of(2008, 12, 31), now)
        assertEventAfterNow(event = LocalDate.of(2013, 8, 17), now)

        assertEventAfterNow(event = LocalDate.of(2999, 2, 10), now)
        assertEventAfterNow(event = LocalDate.of(4321, 12, 4), now)
        assertEventAfterNow(event = LocalDate.of(3753, 4, 19), now)
        assertEventAfterNow(event = LocalDate.of(2154, 8, 30), now)
    }


    private fun assertEventBeforeNow(event: LocalDate, now: LocalDate) {
        val nextEvent = makeNextEvent(now)

        val expected = LocalDate.of(now.year + 1, event.month, event.dayOfMonth)
        val actual: LocalDate = nextEvent.nextEvent(event)

        assertEquals(expected, actual)
    }

    private fun assertEventAfterNow(event: LocalDate, now: LocalDate) {
        val nextEvent = makeNextEvent(now)

        val expected: LocalDate = event.withYear(now.year)
        val actual: LocalDate = nextEvent.nextEvent(event)

        assertEquals(expected, actual)
    }


    private class TestEventIsToday : EventIsToday {

        val calledList = mutableListOf<LocalDate>()
        var result: Boolean? = null

        override fun isToday(date: LocalDate): Boolean {
            calledList.add(date)
            return result ?: error("result not initialized")
        }
    }

    private class TestIsLeapDay : IsLeapDay {

        var count: Int = 0
        var result: Boolean? = null

        override fun isLeapDay(date: LocalDate): Boolean {
            ++count
            return result ?: error("result not initialized")
        }
    }

    private class TestDetermineLeapDay : DetermineLeapDay {

        var result: Int? = null
        val calledList = mutableListOf<Int>()

        override fun leapDay(year: Int): Int {
            calledList.add(year)
            return result ?: error("result not initialized")
        }
    }
}