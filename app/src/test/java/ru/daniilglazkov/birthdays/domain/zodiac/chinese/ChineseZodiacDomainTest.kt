package ru.daniilglazkov.birthdays.domain.zodiac.chinese

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Danil Glazkov on 11.03.2023, 13:20
 */
class ChineseZodiacDomainTest {

    @Test
    fun test_matches() {
        val zodiac = ChineseZodiacDomain.Base(100, "zodiac")

        assertTrue(zodiac.matches(100))

        assertFalse(zodiac.matches(99))
        assertFalse(zodiac.matches(101))
        assertFalse(zodiac.matches(1))
        assertFalse(zodiac.matches(0))
        assertFalse(zodiac.matches(-1))
    }

    @Test
    fun test_zodiacs() {
        assertTrue(ChineseZodiacDomain.Monkey("").matches(0))
        assertTrue(ChineseZodiacDomain.Rooster("").matches(1))
        assertTrue(ChineseZodiacDomain.Dog("").matches(2))
        assertTrue(ChineseZodiacDomain.Pig("").matches(3))
        assertTrue(ChineseZodiacDomain.Rat("").matches(4))
        assertTrue(ChineseZodiacDomain.Ox("").matches(5))
        assertTrue(ChineseZodiacDomain.Tiger("").matches(6))
        assertTrue(ChineseZodiacDomain.Rabbit("").matches(7))
        assertTrue(ChineseZodiacDomain.Dragon("").matches(8))
        assertTrue(ChineseZodiacDomain.Snake("").matches(9))
        assertTrue(ChineseZodiacDomain.Horse("").matches(10))
        assertTrue(ChineseZodiacDomain.Goat("").matches(11))
    }
}