package ru.daniilglazkov.birthdays.domain.range

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class RangeTest {

    private class TestRange(vararg ranges: IntRange) : Range.Abstract<Int>(*ranges)


    @Test
    fun test_in_range() {
        val rangeCategory = TestRange(-10..10, 20..30, 100..200)

        assertTrue(rangeCategory.contains(-10))
        assertTrue(rangeCategory.contains(10))
        assertTrue(rangeCategory.contains(5))
        assertTrue(rangeCategory.contains(8))
        assertTrue(rangeCategory.contains(21))
        assertTrue(rangeCategory.contains(22))
        assertTrue(rangeCategory.contains(29))
        assertTrue(rangeCategory.contains(120))
        assertTrue(rangeCategory.contains(200))
    }

    @Test
    fun test_out_of_range() {
        val rangeCategory = TestRange(-10..10, 20..30, 100..200)

        assertFalse(rangeCategory.contains(11))
        assertFalse(rangeCategory.contains(15))
        assertFalse(rangeCategory.contains(19))
        assertFalse(rangeCategory.contains(40))
        assertFalse(rangeCategory.contains(-11))
        assertFalse(rangeCategory.contains(1000))
        assertFalse(rangeCategory.contains(-1000))
        assertFalse(rangeCategory.contains(31))
        assertFalse(rangeCategory.contains(32))
    }

    @Test
    fun test_empty_ranges() {
        val rangeCategory = TestRange()

        assertFalse(rangeCategory.contains(0))
        assertFalse(rangeCategory.contains(1))
        assertFalse(rangeCategory.contains(-1))
        assertFalse(rangeCategory.contains(10))
        assertFalse(rangeCategory.contains(-10))
    }
}

