package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.split

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeGroups
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age.AgeRange
import ru.daniilglazkov.birthdays.domain.date.TestCalculateNextEvent
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate

/**
 * @author Danil Glazkov on 31.01.2023, 23:14
 */
class BirthdayListSplitPredicateTest {

    private val type = BirthdayType.Mock

    @Test
    fun test_year() {
        val predicate = BirthdayListSplitPredicate.Year()

        assertEquals(2002, predicate.map(0, "", LocalDate.of(2002, 1, 1), type))
        assertEquals(1970, predicate.map(0, "", LocalDate.of(1970, 1, 1), type))
        assertEquals(1900, predicate.map(0, "", LocalDate.of(1900, 1, 1), type))
        assertEquals(2020, predicate.map(0, "", LocalDate.of(2020, 1, 1), type))
    }

    @Test
    fun test_month() {
        val predicate = BirthdayListSplitPredicate.Month()

        assertEquals(1, predicate.map(0, "", LocalDate.of(2000, 1, 1), type))
        assertEquals(10, predicate.map(0, "", LocalDate.of(2000, 10, 1), type))
        assertEquals(7, predicate.map(0, "", LocalDate.of(2000, 7, 1), type))
        assertEquals(12, predicate.map(0, "", LocalDate.of(2000, 12, 1), type))
    }

    @Test
    fun test_first_character_of_name() {
        val date = LocalDate.MIN

        val predicate = BirthdayListSplitPredicate.FirstCharacterOfName()

        assertEquals("", predicate.map(0, "", date, type))
        assertEquals("N", predicate.map(0, "name", date, type))
        assertEquals("_", predicate.map(0, "_name", date, type))
        assertEquals("T", predicate.map(0, "Text", date, type))
        assertEquals("A", predicate.map(0, "a", date, type))
        assertEquals("A", predicate.map(0, "A", date, type))
        assertEquals("1", predicate.map(0, "123", date, type))
    }

    @Test
    fun test_year_and_month_next_event() {
        val firstPredicate = BirthdayListSplitPredicate.YearAndMonthNextEvent(
            TestCalculateNextEvent(LocalDate.of(2020, 6, 1))
        )
        val secondPredicate = BirthdayListSplitPredicate.YearAndMonthNextEvent(
            TestCalculateNextEvent(LocalDate.of(2000, 9, 1))
        )
        val thirdPredicate = BirthdayListSplitPredicate.YearAndMonthNextEvent(
            TestCalculateNextEvent(LocalDate.of(1977, 1, 1))
        )
        assertEquals("2020 6", firstPredicate.map(0, "", LocalDate.MIN, type))
        assertEquals("2000 9", secondPredicate.map(0, "", LocalDate.MIN, type))
        assertEquals("1977 1", thirdPredicate.map(0, "", LocalDate.MIN, type))
    }

    @Test
    fun test_difference() {
        val dateDifference = DateDifference.Test()
        val range = TestAgeGroups()

        range.result = AgeRange.Base(0..1000)
        dateDifference.result = 1234

        val predicate = BirthdayListSplitPredicate.Difference(dateDifference, range)

        assertEquals(range.result, predicate.map(0, "", LocalDate.MAX, type))

        assertEquals(1, dateDifference.calledList.size)
        assertEquals(LocalDate.MAX, dateDifference.calledList[0])

        assertEquals(1, range.calledList.size)
        assertEquals(dateDifference.result, range.calledList[0])
    }

    @Test
    fun test_zodiac() {
        val date = LocalDate.of(2002, 7, 12)

        val zodiac = GreekZodiacDomain.Base(1, "a")
        val group = TestGreekZodiacGroups(zodiac)

        val predicate = BirthdayListSplitPredicate.Zodiac(group)

        assertEquals(zodiac, predicate.map(0, "", date, type))
        assertEquals(1, group.calledList.size)
        assertEquals(date.dayOfYear, group.calledList[0])
    }


    private class TestGreekZodiacGroups(var result: GreekZodiacDomain) : GreekZodiacGroups {
        val calledList = mutableListOf<Int>()

        override fun group(value: Int): GreekZodiacDomain {
            calledList.add(value)
            return result
        }
    }

    private class TestAgeGroups : AgeGroups {

        lateinit var result: AgeRange
        val calledList = mutableListOf<Int>()

        override fun group(value: Int): AgeRange {
            calledList.add(value)
            return result
        }
    }
}