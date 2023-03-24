package ru.daniilglazkov.birthdays.service.birthday.notification

import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore
import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import java.time.LocalDate

/**
 * @author Danil Glazkov on 19.03.2023, 10:39
 */
class BirthdayNotificationMapperTest : BaseDataTest() {

    private lateinit var resources: BaseUiTest.TestManageResources
    private lateinit var nextEvent: TestCalculateNextEvent
    private lateinit var eventIsToday: TestEventIsToday
    private lateinit var manageStatus: TestManageNotificationStatus
    private lateinit var dateDifference: DateDifference.Test

    private lateinit var mapper: BirthdayNotificationMapper

    private val id = 100
    private val type = BirthdayType.Mock
    private val skip = BirthdayNotification.Skip
    private val name = "name"

    private val testNotification = object : BirthdayNotification {
        override fun notify(context: Context) = Unit
    }

    private val birthdayToday = BirthdayNotification.ReminderMaxPriority(id, "", "")
    private val birthdayTomorrow = BirthdayNotification.ReminderMaxPriority(id, "", "")
    private val birthdayInAWeek = BirthdayNotification.Reminder(id, "", "")

    private val currentDate = LocalDate.of(2023, 3, 15)
    private val oneDayBeforeBirthday = currentDate.minusDays(1)
    private val oneWeekBeforeBirthday = currentDate.minusDays(7)
    private val dateWithoutNotice = LocalDate.of(2023, 10, 10)

    private val anyNumberOfRepetitions = 5


    @Before
    fun setUp() {
        nextEvent = TestCalculateNextEvent(LocalDate.MAX)
        eventIsToday = TestEventIsToday()
        dateDifference = DateDifference.Test()
        manageStatus = TestManageNotificationStatus()

        resources = BaseUiTest.TestManageResources()
        resources.string = "content"

        mapper = BirthdayNotificationMapper.Base(
            resources, manageStatus, nextEvent, eventIsToday,
            dateDifference,
        )
    }

    @Test
    fun test_next_mapper() {
        val nextMapperCalledList = mutableListOf<BirthdayDomain.Base>()

        val nextMapper = object : BirthdayNotificationMapper {
            override fun map(
                id: Int, name: String, date: LocalDate, type: BirthdayType
            ) : BirthdayNotification {
                nextMapperCalledList.add(BirthdayDomain.Base(id, name, date))
                return testNotification
            }
        }

        val mapper = BirthdayNotificationMapper.Base(
            resources, manageStatus, nextEvent, eventIsToday,
            dateDifference,
            nextMapper
        )
        val id = 123
        dateDifference.result = 1000
        eventIsToday.result = false
        nextEvent.result = LocalDate.MIN

        assertEquals(testNotification, mapper.map(id, name, LocalDate.MIN, type))

        assertEquals(1, manageStatus.resetCalledList.size)
        assertCollectionEquals(
            listOf(
                "notification_birthday_today_$id",
                "notification_birthday_tomorrow_$id",
                "notification_birthday_in_a_week_$id",
            ),
            manageStatus.resetCalledList[0]
        )

        assertEquals(1, nextMapperCalledList.size)
        assertEquals(BirthdayDomain.Base(id, name, LocalDate.MIN), nextMapperCalledList[0])
    }

    @Test
    fun test_event_tomorrow() {
        dateDifference.result = 1
        nextEvent.result = LocalDate.MIN
        eventIsToday.result = false

        testMap(
            BirthdayNotification.ReminderMaxPriority(id, name, resources.string),
            "notification_birthday_tomorrow_$id"
        )
    }

    @Test
    fun test_event_in_a_week() {
        dateDifference.result = 7
        nextEvent.result = LocalDate.MIN
        eventIsToday.result = false

        testMap(
            BirthdayNotification.Reminder(id, name, resources.string),
            "notification_birthday_in_a_week_$id"
        )
    }

    @Test
    fun test_event_is_today() {
        dateDifference.result = 1000
        nextEvent.result = LocalDate.MIN
        eventIsToday.result = true

        testMap(
            BirthdayNotification.ReminderMaxPriority(id, name, resources.string),
            "notification_birthday_today_$id"
        )
    }

    @Test
    fun test_multiple_calls() {
        val dataStore = TestDataStore<Boolean>()

        makeMapper(currentDate.plusDays(1), dataStore).let { mapper ->
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        makeMapper(currentDate, dataStore).let { mapper ->
            assertEquals(birthdayToday, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        makeMapper(oneWeekBeforeBirthday, dataStore).let { mapper ->
            assertEquals(birthdayInAWeek, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        makeMapper(oneDayBeforeBirthday, dataStore).let { mapper ->
            assertEquals(birthdayTomorrow, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        dataStore.data.clear()

        makeMapper(currentDate, dataStore).let { mapper ->
            assertEquals(birthdayToday, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        dataStore.data.clear()

        makeMapper(oneDayBeforeBirthday, dataStore).let { mapper ->
            assertEquals(birthdayTomorrow, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        dataStore.data.clear()

        makeMapper(oneWeekBeforeBirthday, dataStore).let { mapper ->
            assertEquals(birthdayInAWeek, mapper.map())
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }
        dataStore.data.clear()

        makeMapper(currentDate.minusDays(8), dataStore).let { mapper ->
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }

        makeMapper(currentDate.plusDays(1), dataStore).let { mapper ->
            repeat(anyNumberOfRepetitions) { assertEquals(skip, mapper.map()) }
        }
    }

    @Test
    fun test_recurring_calls_with_notifications() {
        val dataStore = TestDataStore<Boolean>()

        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        dataStore.data.clear()

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        dataStore.data.clear()

        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        dataStore.data.clear()


        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())

        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())

        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
    }

    @Test
    fun test_recurring_calls_with_no_notification_days() {
        val dataStore = TestDataStore<Boolean>()

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())

        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())

        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())

        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())

        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())

        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())

        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())

        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())

        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(dateWithoutNotice, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
    }

    @Test
    fun test_base() {
        val dataStore = TestDataStore<Boolean>()

        makeMapper(currentDate.minusDays(9), dataStore).let {
            assertEquals(skip, it.map())
            assertEquals(skip, it.map())
        }
        assertEquals(skip, makeMapper(currentDate.minusDays(8), dataStore).map())

        makeMapper(currentDate.minusDays(7), dataStore).let {
            assertEquals(birthdayInAWeek, it.map())
            assertEquals(skip, it.map())
        }
        makeMapper(currentDate.minusDays(6), dataStore).let {
            assertEquals(skip, it.map())
            assertEquals(skip, it.map())
        }
        assertEquals(skip, makeMapper(currentDate.minusDays(5), dataStore).map())
        assertEquals(skip, makeMapper(currentDate.minusDays(2), dataStore).map())

        makeMapper(currentDate.minusDays(1), dataStore).let {
            assertEquals(birthdayTomorrow, it.map())
            assertEquals(skip, it.map())
        }

        makeMapper(currentDate, dataStore).let {
            assertEquals(birthdayToday, it.map())
            assertEquals(skip, it.map())
        }

        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())


        assertEquals(birthdayInAWeek, makeMapper(oneWeekBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(oneWeekBeforeBirthday, dataStore).map())

        assertEquals(birthdayTomorrow, makeMapper(oneDayBeforeBirthday, dataStore).map())
        assertEquals(skip, makeMapper(oneDayBeforeBirthday, dataStore).map())

        assertEquals(birthdayToday, makeMapper(currentDate, dataStore).map())
        assertEquals(skip, makeMapper(currentDate, dataStore).map())

        assertEquals(skip, makeMapper(currentDate.plusDays(1), dataStore).map())
        assertEquals(skip, makeMapper(currentDate.plusDays(7), dataStore).map())
    }


    private fun testMap(targetNotification: BirthdayNotification, targetKey: String) {
        val date = LocalDate.MIN
        manageStatus.fetchResult = testNotification

        assertEquals(testNotification, mapper.map(id, name, date, type))

        assertEquals(1, manageStatus.fetchCalledListNotification.size)
        assertEquals(targetNotification, manageStatus.fetchCalledListNotification[0])

        assertEquals(1, nextEvent.calledList.size)
        assertEquals(date, nextEvent.calledList[0])

        assertEquals(1, dateDifference.calledList.size)
        assertEquals(nextEvent.result, dateDifference.calledList[0])

        assertCollectionEquals(
            listOf(targetKey),
            manageStatus.fetchCalledListTargetKey
        )
        assertCollectionEquals(
            listOf(
                "notification_birthday_today_$id",
                "notification_birthday_tomorrow_$id",
                "notification_birthday_in_a_week_$id"
            ),
            manageStatus.fetchCalledListAllKeys[0]
        )
        assertEquals(1, manageStatus.fetchCalledListTargetKey.size)
        assertEquals(targetKey, manageStatus.fetchCalledListTargetKey[0])
        assertEquals(1, manageStatus.fetchCalledListAllKeys.size)

        assertEquals(0, manageStatus.resetCalledList.size)
    }

    private fun BirthdayNotificationMapper.map() = map(id, "", currentDate, type)

    private fun makeMapper(
        currentDate: LocalDate,
        dataStore: PreferencesDataStore.Mutable<Boolean>,
    ) = BirthdayNotificationMapper.Base(
        BaseUiTest.TestManageResources(),
        ManageNotificationDisplayStatus.Base(dataStore),
        CalculateNextEvent.Base(
            EventIsToday.Base(currentDate),
            IsLeapDay.Base(),
            DetermineLeapDay.Base(),
            currentDate
        ),
        EventIsToday.Base(currentDate),
        DateDifference.Days.Base(currentDate)
    )


    private class TestManageNotificationStatus : ManageNotificationDisplayStatus {

        val resetCalledList = mutableListOf<List<String>>()

        val fetchCalledListNotification = mutableListOf<BirthdayNotification>()
        val fetchCalledListTargetKey = mutableListOf<String>()
        val fetchCalledListAllKeys = mutableListOf<List<String>>()

        lateinit var fetchResult: BirthdayNotification

        override fun fetchBasedOnStatus(
            targetNotification: BirthdayNotification,
            targetKey: String,
            allKeys: List<String>
        ) : BirthdayNotification {
            fetchCalledListNotification.add(targetNotification)
            fetchCalledListTargetKey.add(targetKey)
            fetchCalledListAllKeys.add(allKeys)

            return fetchResult
        }

        override fun resetStatus(keys: List<String>) { resetCalledList.add(keys) }
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