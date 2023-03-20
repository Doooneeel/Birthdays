package ru.daniilglazkov.birthdays.domain.core.text

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.datetime.*
import java.time.LocalDate
import java.time.Month
import java.util.*

/**
 * @author Danil Glazkov on 02.02.2023, 1:43
 */
class DateFormatTextFormatTest {

    private val english = Locale.ENGLISH
    private val germany = Locale.GERMANY
    private val russian = Locale("ru")

    @Test
    fun test_month() {
        val englishTextFormat = DateTextFormat.Month(english)
        val germanyTextFormat = DateTextFormat.Month(germany)
        val russianTextFormat = DateTextFormat.Month(russian)

        assertEquals("January", englishTextFormat.format(LocalDate.of(1, Month.JANUARY, 1)))
        assertEquals("December", englishTextFormat.format(LocalDate.of(1, Month.DECEMBER, 1)))
        assertEquals("May", englishTextFormat.format(LocalDate.of(1, Month.MAY, 1)))
        assertEquals("February", englishTextFormat.format(LocalDate.of(1, Month.FEBRUARY, 1)))
        assertEquals("June", englishTextFormat.format(LocalDate.of(1, Month.JUNE, 1)))
        assertEquals("April", englishTextFormat.format(LocalDate.of(1, Month.APRIL, 1)))

        assertEquals("Januar", germanyTextFormat.format(LocalDate.of(1, Month.JANUARY, 1)))
        assertEquals("Dezember", germanyTextFormat.format(LocalDate.of(1, Month.DECEMBER, 1)))
        assertEquals("Oktober", germanyTextFormat.format(LocalDate.of(1, Month.OCTOBER, 1)))

        assertEquals("Май", russianTextFormat.format(LocalDate.of(1, Month.MAY, 1)))
        assertEquals("Октябрь", russianTextFormat.format(LocalDate.of(1, Month.OCTOBER, 1)))
        assertEquals("Сентябрь", russianTextFormat.format(LocalDate.of(1, Month.SEPTEMBER, 1)))
        assertEquals("Январь", russianTextFormat.format(LocalDate.of(1, Month.JANUARY, 1)))
        assertEquals("Апрель", russianTextFormat.format(LocalDate.of(1, Month.APRIL, 1)))
        assertEquals("Август", russianTextFormat.format(LocalDate.of(1, Month.AUGUST, 1)))
    }

    @Test
    fun test_year() {
        val textFormat = DateTextFormat.Year()

        assertEquals("2000", textFormat.format(LocalDate.of(2000, 1, 1)))
        assertEquals("1970", textFormat.format(LocalDate.of(1970, 12, 5)))
        assertEquals("1203", textFormat.format(LocalDate.of(1203, 2, 2)))
        assertEquals("2020", textFormat.format(LocalDate.of(2020, 2, 29)))
        assertEquals("2013", textFormat.format(LocalDate.of(2013, 2, 28)))
    }

    @Test
    fun test_month_and_year() {
        val nextEvent = TestCalculateNextEvent(result = LocalDate.of(1978, 1, 1))

        val englishTextFormat = DateTextFormat.MonthAndYearOfNextEvent(nextEvent, english)
        val germanyTextFormat = DateTextFormat.MonthAndYearOfNextEvent(nextEvent, germany)
        val russianTextFormat = DateTextFormat.MonthAndYearOfNextEvent(nextEvent, russian)

        assertEquals("January 1978", englishTextFormat.format(LocalDate.of(1, Month.JANUARY, 11)))
        assertEquals("December 1978", englishTextFormat.format(LocalDate.of(1, Month.DECEMBER, 14)))
        assertEquals("September 1978", englishTextFormat.format(LocalDate.of(1, Month.SEPTEMBER, 23)))
        assertEquals("May 1978", englishTextFormat.format(LocalDate.of(1, Month.MAY, 23)))
        assertEquals("April 1978", englishTextFormat.format(LocalDate.of(1, Month.APRIL, 23)))
        assertEquals("August 1978", englishTextFormat.format(LocalDate.of(1, Month.AUGUST, 23)))

        assertEquals("April 1978", germanyTextFormat.format(LocalDate.of(1, Month.APRIL, 11)))
        assertEquals("August 1978", germanyTextFormat.format(LocalDate.of(1, Month.AUGUST, 14)))
        assertEquals("September 1978", germanyTextFormat.format(LocalDate.of(1, Month.SEPTEMBER, 23)))

        assertEquals("Август 1978", russianTextFormat.format(LocalDate.of(1, Month.AUGUST, 11)))
        assertEquals("Декабрь 1978", russianTextFormat.format(LocalDate.of(1, Month.DECEMBER, 14)))
        assertEquals("Ноябрь 1978", russianTextFormat.format(LocalDate.of(1, Month.NOVEMBER, 23)))
        assertEquals("Май 1978", russianTextFormat.format(LocalDate.of(1, Month.MAY, 23)))
        assertEquals("Апрель 1978", russianTextFormat.format(LocalDate.of(1, Month.APRIL, 23)))
        assertEquals("Сентябрь 1978", russianTextFormat.format(LocalDate.of(1, Month.SEPTEMBER, 23)))
    }

    @Test
    fun test_day_of_week() {
        val englishTextFormat = DateTextFormat.DayOfWeek(english)
        val germanyTextFormat = DateTextFormat.DayOfWeek(germany)
        val russianTextFormat = DateTextFormat.DayOfWeek(russian)

        assertEquals("Sunday", englishTextFormat.format(LocalDate.of(2002, Month.DECEMBER, 15)))
        assertEquals("Wednesday", englishTextFormat.format(LocalDate.of(2020, Month.MAY, 20)))
        assertEquals("Tuesday", englishTextFormat.format(LocalDate.of(2030, Month.SEPTEMBER, 10)))
        assertEquals("Monday", englishTextFormat.format(LocalDate.of(2020, Month.JUNE, 29)))
        assertEquals("Friday", englishTextFormat.format(LocalDate.of(2023, Month.AUGUST, 25)))

        assertEquals("Donnerstag", germanyTextFormat.format(LocalDate.of(2015, Month.OCTOBER, 15)))
        assertEquals("Samstag", germanyTextFormat.format(LocalDate.of(2019, Month.MAY, 11)))
        assertEquals("Samstag", germanyTextFormat.format(LocalDate.of(2020, Month.FEBRUARY, 29)))

        assertEquals("Четверг", russianTextFormat.format(LocalDate.of(2015, Month.OCTOBER, 15)))
        assertEquals("Воскресенье", russianTextFormat.format(LocalDate.of(2019, Month.MAY, 12)))
        assertEquals("Суббота", russianTextFormat.format(LocalDate.of(2020, Month.FEBRUARY, 29)))
        assertEquals("Четверг", russianTextFormat.format(LocalDate.of(2023, Month.FEBRUARY, 2)))
        assertEquals("Суббота", russianTextFormat.format(LocalDate.of(2023, Month.SEPTEMBER, 9)))
    }

    @Test
    fun test_full() {
        val englishTextFormat = DateTextFormat.Full(english)
        val germanyTextFormat = DateTextFormat.Full(germany)
        val russianTextFormat = DateTextFormat.Full(russian)

        assertEquals("Saturday, January 11, 2020",
            englishTextFormat.format(LocalDate.of(2020, Month.JANUARY, 11))
        )
        assertEquals("Monday, May 17, 1971",
            englishTextFormat.format(LocalDate.of(1971, Month.MAY, 17))
        )
        assertEquals("Monday, December 12, 2033",
            englishTextFormat.format(LocalDate.of(2033, Month.DECEMBER, 12))
        )
        assertEquals("Wednesday, September 1, 1700",
            englishTextFormat.format(LocalDate.of(1700, Month.SEPTEMBER, 1))
        )

        assertEquals("Montag, 2. Dezember 1833",
            germanyTextFormat.format(LocalDate.of(1833, Month.DECEMBER, 2))
        )
        assertEquals("Sonntag, 12. Oktober 2031",
            germanyTextFormat.format(LocalDate.of(2031, Month.OCTOBER, 12))
        )

        assertEquals("Суббота, 11 января 2020 г.",
            russianTextFormat.format(LocalDate.of(2020, Month.JANUARY, 11))
        )
        assertEquals("Понедельник, 17 мая 1971 г.",
            russianTextFormat.format(LocalDate.of(1971, Month.MAY, 17))
        )
        assertEquals("Четверг, 29 февраля 2024 г.",
            russianTextFormat.format(LocalDate.of(2024, Month.FEBRUARY, 29))
        )
        assertEquals("Воскресенье, 15 июня 2166 г.",
            russianTextFormat.format(LocalDate.of(2166, Month.JUNE, 15))
        )
    }
}