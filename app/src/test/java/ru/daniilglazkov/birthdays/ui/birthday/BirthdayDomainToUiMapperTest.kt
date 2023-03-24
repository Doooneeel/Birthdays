package ru.daniilglazkov.birthdays.ui.birthday

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.core.text.format.DaysToEventTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 06.02.2023, 8:01
 */
class BirthdayDomainToUiMapperTest {

    @Test
    fun test_map() {
        val date = LocalDate.of(2020, 5, 1)
        val nextEvent = LocalDate.MIN
        val daysToEvent = "daysToEvent"

        val dateTextFormat = TestDateTextFormat()
        val daysToEventTextFormat = TestDaysToEventTextFormat(daysToEvent)
        val turnsYearsOld = DateDifference.Test()
        val daysToBirthday = DateDifference.Test()
        val calculateNextEvent = TestCalculateNextEvent(nextEvent)
        val mangeResources = BaseUiTest.TestManageResources()

        turnsYearsOld.result = 1000
        daysToBirthday.result = 120

        val birthdayDomainToUiMapper = BirthdayDomainToUiMapper.Base(
            dateTextFormat,
            daysToEventTextFormat,
            turnsYearsOld,
            daysToBirthday,
            calculateNextEvent,
            mangeResources
        )

        val expected = BirthdayUi.Base(
            name = "name",
            birthdate = date.toString(),
            birthday = nextEvent.toString(),
            turnsYearsOld = turnsYearsOld.result.toString(),
            daysLeft = DaysLeft.Base(daysToBirthday.result.toString(), daysToEvent)
        )
        val actual: BirthdayUi = birthdayDomainToUiMapper.map(10, "name", date, BirthdayType.Mock)

        assertEquals(expected, actual)

        assertEquals(1, calculateNextEvent.calledList.size)
        assertEquals(date, calculateNextEvent.calledList[0])

        assertEquals(1, turnsYearsOld.calledList.size)
        assertEquals(date, turnsYearsOld.calledList[0])

        assertEquals(1, daysToBirthday.calledList.size)
        assertEquals(date, daysToBirthday.calledList[0])

        assertEquals(2, mangeResources.quantityStringCalledList.size)

        assertTrue(mangeResources.quantityStringCalledList.containsAll(
            listOf(daysToBirthday.result, turnsYearsOld.result)
        ))

        assertEquals(1, daysToEventTextFormat.calledList.size)
        assertEquals(daysToBirthday.result, daysToEventTextFormat.calledList[0])
    }


    private class TestDaysToEventTextFormat(private val result: String) : DaysToEventTextFormat {

        val calledList = mutableListOf<Int>()

        override fun format(source: Int): String {
            calledList.add(source)
            return result
        }
    }

    private class TestDateTextFormat : DateTextFormat {
        override fun format(source: LocalDate): String = source.toString()
    }
}