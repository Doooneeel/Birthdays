package ru.daniilglazkov.birthdays.domain.birthday

import org.junit.Assert.*
import org.junit.Test
import java.time.LocalDate

/**
 * @author Danil Glazkov on 30.01.2023, 19:10
 */
class BirthdayCheckMapperTest {

    @Test
    fun test_is_header() {
        val mapper = BirthdayCheckMapper.IsHeader

        val type = TestType()

        type.result = true
        assertTrue(mapper.map(0, "", LocalDate.MAX, type))

        assertEquals(1, type.matchesCalledList.size)
        assertEquals(BirthdayType.Header, type.matchesCalledList[0])

        type.result = false
        assertFalse(mapper.map(0, "", LocalDate.MAX, type))

        assertEquals(2, type.matchesCalledList.size)
        assertEquals(BirthdayType.Header, type.matchesCalledList[1])
    }

    @Test
    fun test_is_not_header() {
        val mapper = BirthdayCheckMapper.IsNotHeader
        val type = TestType()

        type.result = true
        assertFalse(mapper.map(0, "", LocalDate.MAX, type))

        assertEquals(1, type.matchesCalledList.size)
        assertEquals(BirthdayType.Header, type.matchesCalledList[0])

        type.result = false
        assertTrue(mapper.map(0, "", LocalDate.MAX, type))

        assertEquals(2, type.matchesCalledList.size)
        assertEquals(BirthdayType.Header, type.matchesCalledList[1])
    }

    @Test
    fun test_compare() {
        val compare = BirthdayCheckMapper.Compare(10, "a", LocalDate.MAX, BirthdayType.Base)

        assertTrue(compare.map(10, "a", LocalDate.MAX, BirthdayType.Base))

        assertFalse(compare.map(1, "a", LocalDate.MAX, BirthdayType.Base))
        assertFalse(compare.map(10, "b", LocalDate.MAX, BirthdayType.Base))
        assertFalse(compare.map(10, "a", LocalDate.MIN, BirthdayType.Base))
        assertFalse(compare.map(10, "a", LocalDate.MAX, BirthdayType.Mock))

        assertFalse(compare.map(9, "", LocalDate.MIN, BirthdayType.Mock))
    }

    @Test
    fun test_compare_object() {
        val compare = BirthdayCheckMapper.CompareObject(
            BirthdayDomain.Base(-1, "name", LocalDate.MAX)
        )

        assertTrue(compare.map(-1, "name", LocalDate.MAX, BirthdayType.Base))

        assertFalse(compare.map(10, "name", LocalDate.MAX, BirthdayType.Base))
        assertFalse(compare.map(-1, "a", LocalDate.MAX, BirthdayType.Base))
        assertFalse(compare.map(-1, "name", LocalDate.MIN, BirthdayType.Base))
        assertFalse(compare.map(-1, "name", LocalDate.MAX, BirthdayType.Mock))

        assertFalse(compare.map(1, "", LocalDate.MIN, BirthdayType.Mock))
    }


    private class TestType : BirthdayType("mock") {

        val matchesCalledList = mutableListOf<BirthdayType>()
        var result = false

        override fun matches(data: BirthdayType): Boolean {
            matchesCalledList.add(data)

            return result
        }
    }
}