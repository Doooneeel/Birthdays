package ru.daniilglazkov.birthdays.ui.zodiac

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Danil Glazkov on 08.03.2023, 18:45
 */
class TestZodiacDomainToUiMapper {

    private val greek = ZodiacDomainToUiMapper.Greek()
    private val chinese = ZodiacDomainToUiMapper.Chinese()

    @Test
    fun test_chinese() {
        assertEquals(ZodiacUi.Aries, greek.map(0, ""))
        assertEquals(ZodiacUi.Taurus, greek.map(1, ""))
        assertEquals(ZodiacUi.Gemini, greek.map(2, ""))
        assertEquals(ZodiacUi.Cancer, greek.map(3, ""))
        assertEquals(ZodiacUi.Leo, greek.map(4, ""))
        assertEquals(ZodiacUi.Virgo, greek.map(5, ""))
        assertEquals(ZodiacUi.Libra, greek.map(6, ""))
        assertEquals(ZodiacUi.Scorpio, greek.map(7, ""))
        assertEquals(ZodiacUi.Sagittarius, greek.map(8, ""))
        assertEquals(ZodiacUi.Capricorn, greek.map(9, ""))
        assertEquals(ZodiacUi.Aquarius, greek.map(10, ""))
        assertEquals(ZodiacUi.Pisces, greek.map(11, ""))
    }

    @Test
    fun test_greek() {
        assertEquals(ZodiacUi.Monkey, chinese.map(0, ""))
        assertEquals(ZodiacUi.Rooster, chinese.map(1, ""))
        assertEquals(ZodiacUi.Dog, chinese.map(2, ""))
        assertEquals(ZodiacUi.Pig, chinese.map(3, ""))
        assertEquals(ZodiacUi.Rat, chinese.map(4, ""))
        assertEquals(ZodiacUi.Ox, chinese.map(5, ""))
        assertEquals(ZodiacUi.Tiger, chinese.map(6, ""))
        assertEquals(ZodiacUi.Rabbit, chinese.map(7, ""))
        assertEquals(ZodiacUi.Dragon, chinese.map(8, ""))
        assertEquals(ZodiacUi.Snake, chinese.map(9, ""))
        assertEquals(ZodiacUi.Horse, chinese.map(10, ""))
        assertEquals(ZodiacUi.Goat, chinese.map(11, ""))
    }

    @Test
    fun test_chinese_invalid_ordinal() {
        assertEquals(ZodiacUi.Error, chinese.map(1000, ""))
        assertEquals(ZodiacUi.Error, chinese.map(-1000, ""))
        assertEquals(ZodiacUi.Error, chinese.map(-1, ""))
        assertEquals(ZodiacUi.Error, chinese.map(12, ""))
    }

    @Test
    fun test_greek_invalid_ordinal() {
        assertEquals(ZodiacUi.Error, greek.map(1000, ""))
        assertEquals(ZodiacUi.Error, greek.map(-1000, ""))
        assertEquals(ZodiacUi.Error, greek.map(-1, ""))
        assertEquals(ZodiacUi.Error, greek.map(12, ""))
    }
}