package ru.daniilglazkov.birthdays.domain.range

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 06.03.2023, 16:50
 */
class RangeGroupsTest {

    private val range_m20_m5 = Range.Base(-20..-5)
    private val range_0_5 = Range.Base(0..5)
    private val range_7_12 = Range.Base(7..12)
    private val range_20_40 = Range.Base(20..40)

    private val rangeGroups = RangeGroups.Base<Range<Int>, Int>(
        listOf(range_m20_m5, range_0_5, range_7_12, range_20_40)
    )

    @Test
    fun test_in_range() {
        assertEquals(range_7_12, rangeGroups.group(10))
        assertEquals(range_20_40, rangeGroups.group(20))
        assertEquals(range_20_40, rangeGroups.group(21))
        assertEquals(range_20_40, rangeGroups.group(40))

        assertEquals(range_m20_m5, rangeGroups.group(-20))
        assertEquals(range_m20_m5, rangeGroups.group(-10))
        assertEquals(range_m20_m5, rangeGroups.group(-5))

        assertEquals(range_7_12, rangeGroups.group(7))
        assertEquals(range_7_12, rangeGroups.group(8))
        assertEquals(range_7_12, rangeGroups.group(9))

        assertEquals(range_0_5, rangeGroups.group(0))
        assertEquals(range_0_5, rangeGroups.group(1))
        assertEquals(range_0_5, rangeGroups.group(2))
        assertEquals(range_0_5, rangeGroups.group(3))
        assertEquals(range_0_5, rangeGroups.group(4))
        assertEquals(range_0_5, rangeGroups.group(5))
    }

    @Test
    fun test_out_of_range() {
        val exception = NotFoundException::class.java

        assertThrows(exception) { rangeGroups.group(6) }
        assertThrows(exception) { rangeGroups.group(41) }
        assertThrows(exception) { rangeGroups.group(13) }
        assertThrows(exception) { rangeGroups.group(14) }
        assertThrows(exception) { rangeGroups.group(-1000) }
        assertThrows(exception) { rangeGroups.group(1000) }
        assertThrows(exception) { rangeGroups.group(-21) }
    }
}