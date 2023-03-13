package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.age

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * @author Danil Glazkov on 30.01.2023, 23:55
 */
class AgeRangeTest {

    @Test
    fun test_contains() {
        val rangeCategory = AgeRange.Base(Int.MIN_VALUE..1000)

        assertTrue(rangeCategory.contains(0))
        assertTrue(rangeCategory.contains(1000))
        assertTrue(rangeCategory.contains(-1000))
        assertTrue(rangeCategory.contains(999))
        assertTrue(rangeCategory.contains(Int.MIN_VALUE))

        assertFalse(rangeCategory.contains(Int.MAX_VALUE))
        assertFalse(rangeCategory.contains(1200))
        assertFalse(rangeCategory.contains(1001))
        assertFalse(rangeCategory.contains(10000))
    }
}