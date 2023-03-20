package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.DateDifference
import ru.daniilglazkov.birthdays.domain.datetime.DateTextFormat
import ru.daniilglazkov.birthdays.domain.zodiac.*
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate
import java.time.Month

/**
 * @author Danil Glazkov on 31.01.2023, 16:55
 */
class BirthdayGroupHeaderPredicateTest {
    private val date = LocalDate.of(2020, Month.MARCH, 15)

    @Test
    fun test_date() {
        val predicate = BirthdayGroupHeaderPredicate.DateFormat(
            DateTextFormat.Mock("date format")
        )

        val actual: String = predicate.map(0, "", date, BirthdayType.Mock)
        val expected = "date format"

        assertEquals(expected, actual)
    }

    @Test
    fun test_first_char() {
        val predicate = BirthdayGroupHeaderPredicate.FirstCharacterOfName()

        assertEquals("N", predicate.map(0, "name", date, BirthdayType.Mock))
        assertEquals("T", predicate.map(0, "text", date, BirthdayType.Mock))
        assertEquals("A", predicate.map(0, "Abc", date, BirthdayType.Mock))
        assertEquals("!", predicate.map(0, "!abc", date, BirthdayType.Mock))
        assertEquals("6", predicate.map(0, "625", date, BirthdayType.Mock))
        assertEquals("A", predicate.map(0, "a", date, BirthdayType.Mock))
        assertEquals("T", predicate.map(0, "TEXT", date, BirthdayType.Mock))
        assertEquals("", predicate.map(0, " ", date, BirthdayType.Mock))
        assertEquals("", predicate.map(0, "", date, BirthdayType.Mock))
    }

    @Test
    fun test_range() {
        val predicate1 = BirthdayGroupHeaderPredicate.Difference(DateDifference.Test(10))
        assertEquals("10", predicate1.map(0, "", date, BirthdayType.Mock))

        val predicate2 = BirthdayGroupHeaderPredicate.Difference(DateDifference.Test(0))
        assertEquals("0", predicate2.map(0, "", date, BirthdayType.Mock))

        val predicate3 = BirthdayGroupHeaderPredicate.Difference(DateDifference.Test(-10))
        assertEquals("-10", predicate3.map(0, "", date, BirthdayType.Mock))
    }

    @Test
    fun test_zodiac() {
        val mapperToHeader = object : ZodiacDomain.Mapper<String> {
            override fun map(ordinal: Int, name: String): String = "$ordinal-$name"
        }

        val zodiacGroup = object : GreekZodiacGroups {
            override fun group(value: Int) = GreekZodiacDomain.Base(255, "zodiac name")
        }
        val predicate = BirthdayGroupHeaderPredicate.ZodiacPredicate(zodiacGroup, mapperToHeader)

        assertEquals("255-zodiac name" , predicate.map(0, "", date, BirthdayType.Mock))
    }

}