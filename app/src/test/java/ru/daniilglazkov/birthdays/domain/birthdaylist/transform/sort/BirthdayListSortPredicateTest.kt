package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate
import java.util.*

/**
 * @author Danil Glazkov on 31.01.2023, 17:59
 */
class BirthdayListSortPredicateTest {

    private val date = LocalDate.MIN
    private val type = BirthdayType.Mock

    @Test
    fun test_epoch_day() {
        val epochDayAscending = BirthdayListSortPredicate.EpochDayAscending()

        assertEquals(0, epochDayAscending.map(0, "", LocalDate.of(1970, 1, 1), type))
        assertEquals(365, epochDayAscending.map(0, "", LocalDate.of(1971, 1, 1), type))
        assertEquals(396, epochDayAscending.map(0, "", LocalDate.of(1971, 2, 1), type))
        assertEquals(18336, epochDayAscending.map(0, "", LocalDate.of(2020, 3, 15), type))
        assertEquals(18337, epochDayAscending.map(0, "", LocalDate.of(2020, 3, 16), type))


        val epochDayDescending = BirthdayListSortPredicate.EpochDayDescending()

        assertEquals(0, epochDayDescending.map(0, "", LocalDate.of(1970, 1, 1), type))
        assertEquals(-365, epochDayDescending.map(0, "", LocalDate.of(1971, 1, 1), type))
        assertEquals(-396, epochDayDescending.map(0, "", LocalDate.of(1971, 2, 1), type))
        assertEquals(-18336, epochDayDescending.map(0, "", LocalDate.of(2020, 3, 15), type))
        assertEquals(-18337, epochDayDescending.map(0, "", LocalDate.of(2020, 3, 16), type))
    }

    @Test
    fun test_name() {
        val predicate = BirthdayListSortPredicate.Name(Locale.ENGLISH)

        assertEquals("a", predicate.map(0, "a", date, type))
        assertEquals("", predicate.map(0, "", date, type))
        assertEquals("aaa", predicate.map(0, "AAA", date, type))
        assertEquals(" abc ", predicate.map(0, " abc ", date, type))
        assertEquals(" abc ", predicate.map(0, " aBc ", date, type))
        assertEquals("123", predicate.map(0, "123", date, type))
        assertEquals("", predicate.map(0, "", date, type))
        assertEquals("  ", predicate.map(0, "  ", date, type))
        assertEquals(".", predicate.map(0, ".", date, type))
    }

    @Test
    fun test_day_of_year() {
        val predicate = BirthdayListSortPredicate.DayOfYear()

        assertEquals(1, predicate.map(0, "", LocalDate.of(2020, 1, 1), type))
        assertEquals(15, predicate.map(0, "", LocalDate.of(2020, 1, 15), type))
        assertEquals(60, predicate.map(0, "", LocalDate.of(2020, 2, 29), type))
        assertEquals(365, predicate.map(0, "", LocalDate.of(2018, 12, 31), type))
        assertEquals(366, predicate.map(0, "", LocalDate.of(2000, 12, 31), type))
    }

    @Test
    fun test_zodiac() {
        val result = GreekZodiacDomain.Base(0, "a")
        val greekZodiacGroup = TestGreekZodiacGroups(result)

        val predicate = BirthdayListSortPredicate.Zodiac(greekZodiacGroup)

        assertEquals(result, predicate.map(0, "", date, type))

        assertEquals(1, greekZodiacGroup.calledList.size)
        assertEquals(date.dayOfYear, greekZodiacGroup.calledList[0])
    }

    @Test
    fun test_difference() {
        val dateDifference = DateDifference.Test(10)
        val predicate = BirthdayListSortPredicate.Difference(dateDifference)

        assertEquals(10, predicate.map(0, "", date, type))

        assertEquals(1, dateDifference.calledList.size)
        assertEquals(date, dateDifference.calledList[0])
    }


    private class TestGreekZodiacGroups(private val result: GreekZodiacDomain) : GreekZodiacGroups {
        val calledList = mutableListOf<Int>()

        override fun group(value: Int): GreekZodiacDomain {
            calledList.add(value)
            return result
        }
    }
}