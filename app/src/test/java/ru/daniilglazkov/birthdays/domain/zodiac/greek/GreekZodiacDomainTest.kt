package ru.daniilglazkov.birthdays.domain.zodiac.greek

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate
import java.time.Month

class GreekZodiacDomainTest {

    @Test
    fun test_contains() {
        val piscesStart = LocalDate.of(1, Month.FEBRUARY, 20).dayOfYear
        val piscesEnd = LocalDate.of(1, Month.MARCH, 20).dayOfYear

        val aquariusStart = LocalDate.of(1, Month.JANUARY, 21).dayOfYear
        val aquariusEnd = LocalDate.of(1, Month.FEBRUARY, 19).dayOfYear

        val ariesStart = LocalDate.of(1, Month.MARCH, 21).dayOfYear
        val ariesEnd = LocalDate.of(1, Month.APRIL, 20).dayOfYear

        val taurusStart = LocalDate.of(1, Month.APRIL, 21).dayOfYear
        val taurusEnd = LocalDate.of(1, Month.MAY, 21).dayOfYear

        val geminiStart = LocalDate.of(1, Month.MAY, 22).dayOfYear
        val geminiEnd = LocalDate.of(1, Month.JUNE, 21).dayOfYear

        val cancerStart = LocalDate.of(1, Month.JUNE, 22).dayOfYear
        val cancerEnd = LocalDate.of(1, Month.JULY, 22).dayOfYear

        val leoStart = LocalDate.of(1, Month.JULY, 23).dayOfYear
        val leoEnd = LocalDate.of(1, Month.AUGUST, 23).dayOfYear

        val virgoStart = LocalDate.of(1, Month.AUGUST, 24).dayOfYear
        val virgoEnd = LocalDate.of(1, Month.SEPTEMBER, 23).dayOfYear

        val libraStart = LocalDate.of(1, Month.SEPTEMBER, 24).dayOfYear
        val libraEnd = LocalDate.of(1, Month.OCTOBER, 23).dayOfYear

        val scorpioStart = LocalDate.of(1, Month.OCTOBER, 24).dayOfYear
        val scorpioEnd = LocalDate.of(1, Month.NOVEMBER, 22).dayOfYear

        val sagittariusStart = LocalDate.of(1, Month.NOVEMBER, 23).dayOfYear
        val sagittariusEnd = LocalDate.of(1, Month.DECEMBER, 22).dayOfYear

        val capricornStart1 = LocalDate.of(1, Month.DECEMBER, 23).dayOfYear
        val capricornEnd1 = LocalDate.of(1, Month.DECEMBER, 31).dayOfYear
        val capricornStart2 = LocalDate.of(1, Month.JANUARY, 1).dayOfYear
        val capricornEnd2 = LocalDate.of(1, Month.JANUARY, 20).dayOfYear

        assertZodiacContains(GreekZodiacDomain.Aquarius(""), aquariusStart, aquariusEnd)
        assertZodiacContains(GreekZodiacDomain.Pisces(""), piscesStart, piscesEnd)
        assertZodiacContains(GreekZodiacDomain.Aries(""), ariesStart, ariesEnd)
        assertZodiacContains(GreekZodiacDomain.Taurus(""), taurusStart, taurusEnd)
        assertZodiacContains(GreekZodiacDomain.Gemini(""), geminiStart, geminiEnd)
        assertZodiacContains(GreekZodiacDomain.Cancer(""), cancerStart, cancerEnd)
        assertZodiacContains(GreekZodiacDomain.Leo(""), leoStart, leoEnd)
        assertZodiacContains(GreekZodiacDomain.Virgo(""), virgoStart, virgoEnd)
        assertZodiacContains(GreekZodiacDomain.Libra(""), libraStart, libraEnd)
        assertZodiacContains(GreekZodiacDomain.Scorpio(""), scorpioStart, scorpioEnd)
        assertZodiacContains(GreekZodiacDomain.Sagittarius(""), sagittariusStart, sagittariusEnd)

        assertTrue(GreekZodiacDomain.Capricorn("").contains(capricornStart1))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(360))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(capricornEnd1))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(capricornStart2))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(5))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(10))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(15))
        assertTrue(GreekZodiacDomain.Capricorn("").contains(capricornEnd2))

        assertFalse(GreekZodiacDomain.Capricorn("").contains(capricornStart1 - 1))
        assertFalse(GreekZodiacDomain.Capricorn("").contains(0))
        assertFalse(GreekZodiacDomain.Capricorn("").contains(-1))
        assertFalse(GreekZodiacDomain.Capricorn("").contains(capricornEnd2 + 1))
    }

    @Test
    fun test_matches() {
        assertTrue(GreekZodiacDomain.Base(0, "").matches(0))
        assertTrue(GreekZodiacDomain.Base(10, "").matches(10))
        assertTrue(GreekZodiacDomain.Base(-100, "").matches(-100))
    }

    @Test
    fun test_compare_to() {
        val firstZodiac = GreekZodiacDomain.Base(Int.MAX_VALUE, "")
        val secondZodiac = GreekZodiacDomain.Base(5, "")

        assertEquals(Int.MAX_VALUE - 5, firstZodiac.compareTo(secondZodiac))
        assertEquals(5 - Int.MAX_VALUE, secondZodiac.compareTo(firstZodiac))
    }


    private fun assertZodiacContains(zodiac: GreekZodiacDomain, start: Int, end: Int) {
        for (day in start..end) {
            assertTrue(day.toString(), zodiac.contains(day))
        }

        for (day in end + 1..end + 1000) {
            assertFalse(day.toString(), zodiac.contains(day))
        }

        for (day in start - 1000 until start) {
            assertFalse(day.toString(), zodiac.contains(day))
        }

        assertFalse(zodiac.contains(Int.MAX_VALUE))
        assertFalse(zodiac.contains(Int.MIN_VALUE))
    }
}