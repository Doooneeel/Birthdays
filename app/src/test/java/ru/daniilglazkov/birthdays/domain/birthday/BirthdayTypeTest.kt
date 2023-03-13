package ru.daniilglazkov.birthdays.domain.birthday

import org.junit.Assert.*
import org.junit.Test

/**
 * @author Danil Glazkov on 11.03.2023, 14:12
 */
class BirthdayTypeTest {

    @Test
    fun test_matches() {
        val type1 = object : BirthdayType("type") { }
        val type2 = object : BirthdayType("type") { }

        assertTrue(BirthdayType.Base.matches(BirthdayType.Base))

        assertFalse(BirthdayType.Base.matches(BirthdayType.Header))
        assertFalse(BirthdayType.Base.matches(type1))

        assertTrue(type1.matches(type1))
        assertTrue(type1.matches(type2))
        assertTrue(type2.matches(type2))
        assertTrue(type2.matches(type1))
    }
}