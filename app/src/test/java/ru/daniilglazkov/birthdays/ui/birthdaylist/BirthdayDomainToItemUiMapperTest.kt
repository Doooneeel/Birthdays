package ru.daniilglazkov.birthdays.ui.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.DateDifference
import ru.daniilglazkov.birthdays.domain.datetime.EventIsToday
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.core.text.format.DaysToEventTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 07.02.2023, 9:26
 */
class BirthdayDomainToItemUiMapperTest : BaseUiTest() {

    private lateinit var resources: TestManageResources
    private lateinit var daysToEventTextFormat: TestsDaysToEventTextFormat
    private lateinit var turnsYearsOld: DateDifference.Test
    private lateinit var daysToBirthday: DateDifference.Test
    private lateinit var eventIsToday: TestEventIsToday

    private lateinit var mapper: BirthdayDomainToItemUiMapper


    @Before
    fun setUp() {
        daysToEventTextFormat = TestsDaysToEventTextFormat()
        resources = TestManageResources()
        turnsYearsOld = DateDifference.Test()
        daysToBirthday = DateDifference.Test()
        eventIsToday = TestEventIsToday()

        mapper = BirthdayDomainToItemUiMapper.Base(
            resources,
            daysToEventTextFormat,
            turnsYearsOld,
            daysToBirthday,
            eventIsToday
        )
    }

    @Test
    fun test_base_event_is_today() {
        eventIsToday.result = true
        turnsYearsOld.result = 2000

        val expected = BirthdayItemUi.Today(0, "name", "2000")
        val actual = mapper.map(0, "name", LocalDate.MIN, BirthdayType.Base)

        assertEquals(expected, actual)

        assertEquals(1, resources.quantityStringCalledList.size)
        assertEquals(turnsYearsOld.result, resources.quantityStringCalledList[0])

        assertEquals(0, daysToEventTextFormat.calledList.size)
        assertEquals(0, daysToBirthday.calledList.size)
    }

    @Test
    fun test_base_event_not_today() {
        val date = LocalDate.of(2020, 4, 12)

        eventIsToday.result = false
        turnsYearsOld.result = 1000
        daysToBirthday.result = 120

        val expected = BirthdayItemUi.Base(0, "name", "1000",  "120")
        val actual = mapper.map(0, "name", date, BirthdayType.Base)

        assertEquals(expected, actual)


        assertEquals(1, resources.quantityStringCalledList.size)
        assertEquals(turnsYearsOld.result, resources.quantityStringCalledList[0])

        assertEquals(1, daysToEventTextFormat.calledList.size)
        assertEquals(daysToBirthday.result, daysToEventTextFormat.calledList[0])

        assertEquals(1, daysToBirthday.calledList.size)
        assertEquals(date, daysToBirthday.calledList[0])
    }

    @Test
    fun test_header() {
        val mapper = BirthdayDomainToItemUiMapper.Header()

        assertEquals(
            BirthdayItemUi.Header(2, "a"),
            mapper.map(2, "a", LocalDate.MAX, BirthdayType.Mock)
        )
    }


    private class TestEventIsToday : EventIsToday {

        var result: Boolean = false
        val calledList = mutableListOf<LocalDate>()

        override fun isToday(date: LocalDate): Boolean {
            calledList.add(date)
            return result
        }
    }

    private class TestsDaysToEventTextFormat : DaysToEventTextFormat {
        val calledList = mutableListOf<Int>()

        override fun format(source: Int): String {
            calledList.add(source)
            return source.toString()
        }
    }
}