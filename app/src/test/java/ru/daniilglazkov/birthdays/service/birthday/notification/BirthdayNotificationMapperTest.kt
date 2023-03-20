package ru.daniilglazkov.birthdays.service.birthday.notification

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import java.time.LocalDate

/**
 * @author Danil Glazkov on 19.03.2023, 10:39
 */
class BirthdayNotificationMapperTest : BaseUiTest() {

    private lateinit var resources: TestManageResources
    private lateinit var nextEvent: TestCalculateNextEvent
    private lateinit var eventIsToday: TestEventIsToday
    private lateinit var dateDifference: DateDifference.Test

    private lateinit var mapper: BirthdayNotificationMapper

    @Before
    fun setUp() {
        resources = TestManageResources()
        nextEvent = TestCalculateNextEvent(LocalDate.MAX)
        eventIsToday = TestEventIsToday()
        dateDifference = DateDifference.Test()

        mapper = BirthdayNotificationMapper.Base(
            resources,
            nextEvent,
            eventIsToday,
            dateDifference
        )
    }


    @Test
    fun test_event_not_today_and_not_in_a_week() {
        eventIsToday.result = false
        resources.string = "content"
        nextEvent.result = LocalDate.of(1985, 1, 10)
        dateDifference.result = 6

        val date = LocalDate.MAX

        val actual = mapper.map(0, "", date, BirthdayType.Mock)
        val expected = BirthdayNotification.Skip

        assertEquals(expected, actual)

        assertEquals(1, nextEvent.calledList.size)
        assertEquals(date, nextEvent.calledList[0])

        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(nextEvent.result, eventIsToday.calledList[0])

        assertEquals(1, dateDifference.calledList.size)
        assertEquals(nextEvent.result, dateDifference.calledList[0])

        assertEquals(0, resources.stringCount)

        dateDifference.result = 8
        assertEquals(BirthdayNotification.Skip, mapper.map(0, "", date, BirthdayType.Mock))

        dateDifference.result = 0
        assertEquals(BirthdayNotification.Skip, mapper.map(0, "", date, BirthdayType.Mock))
    }

    @Test
    fun test_event_in_a_week() {
        eventIsToday.result = false
        resources.string = "content"
        nextEvent.result = LocalDate.of(1985, 1, 10)
        dateDifference.result = 7

        val name = "name"
        val id = 120
        val date = LocalDate.MAX

        val actual = mapper.map(id, name, date, BirthdayType.Mock)
        val expected = BirthdayNotification.Reminder(id, name, resources.string)

        assertEquals(actual, expected)

        assertEquals(1, nextEvent.calledList.size)
        assertEquals(date, nextEvent.calledList[0])

        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(nextEvent.result, eventIsToday.calledList[0])

        assertEquals(1, dateDifference.calledList.size)
        assertEquals(nextEvent.result, dateDifference.calledList[0])

        assertEquals(1, resources.stringCount)

        mapper.map(id, name, date, BirthdayType.Mock)

        assertEquals(1, resources.stringCount)
    }

    @Test
    fun test_event_is_today() {
        eventIsToday.result = true
        resources.string = "content"
        nextEvent.result = LocalDate.of(1982, 4, 1)

        val name = "name"
        val id = 100
        val date = LocalDate.MIN

        val actual = mapper.map(id, name, date, BirthdayType.Mock)
        val expected = BirthdayNotification.ReminderMaxPriority(id, name, resources.string)

        assertEquals(actual, expected)

        assertEquals(1, nextEvent.calledList.size)
        assertEquals(date, nextEvent.calledList[0])

        assertEquals(1, eventIsToday.calledList.size)
        assertEquals(nextEvent.result, eventIsToday.calledList[0])

        assertEquals(0, dateDifference.calledList.size)
        assertEquals(1, resources.stringCount)

        mapper.map(id, name, date, BirthdayType.Mock)

        assertEquals(1, resources.stringCount)
    }


    private class TestEventIsToday : EventIsToday {

        val calledList = mutableListOf<LocalDate>()
        var result = false

        override fun isToday(date: LocalDate): Boolean {
            calledList.add(date)
            return result
        }
    }
}