package ru.daniilglazkov.birthdays.domain.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

class BirthdayListSortMapperTest {

    private val source = BirthdayListDomain.Base(listOf(
        BirthdayDomain.Base(3, "AAA", LocalDate.MAX),
        BirthdayDomain.Base(0, "CCC", LocalDate.MAX),
        BirthdayDomain.Base(1, "DDD", LocalDate.MIN),
        BirthdayDomain.Base(2, "BBB", LocalDate.MIN),
        BirthdayDomain.Base(5, "ZZZ", LocalDate.MAX),
        BirthdayDomain.Base(4, "EEE", LocalDate.MIN),
    ))

    private val namePredicate = TestNamePredicate()
    private val idPredicate = TestIdPredicate()

    @Test
    fun test_ascending_name() {
        val sortedList = listOf(
            BirthdayDomain.Base(3, "AAA", LocalDate.MAX),
            BirthdayDomain.Base(2, "BBB", LocalDate.MIN),
            BirthdayDomain.Base(0, "CCC", LocalDate.MAX),
            BirthdayDomain.Base(1, "DDD", LocalDate.MIN),
            BirthdayDomain.Base(4, "EEE", LocalDate.MIN),
            BirthdayDomain.Base(5, "ZZZ", LocalDate.MAX),
        )
        val expectedAscending = BirthdayListDomain.Base(sortedList)
        val expectedDescending = BirthdayListDomain.Base(sortedList.reversed())

        val actualAscending = source.map(BirthdayListSortMapper.Ascending(namePredicate))
        val actualDescending = source.map(BirthdayListSortMapper.Descending(namePredicate))

        assertEquals(expectedAscending, actualAscending)
        assertEquals(expectedDescending, actualDescending)
    }

    @Test
    fun test_ascending_id() {
        val sortedList = listOf(
            BirthdayDomain.Base(0, "CCC", LocalDate.MAX),
            BirthdayDomain.Base(1, "DDD", LocalDate.MIN),
            BirthdayDomain.Base(2, "BBB", LocalDate.MIN),
            BirthdayDomain.Base(3, "AAA", LocalDate.MAX),
            BirthdayDomain.Base(4, "EEE", LocalDate.MIN),
            BirthdayDomain.Base(5, "ZZZ", LocalDate.MAX),
        )
        val expectedAscending = BirthdayListDomain.Base(sortedList)
        val expectedDescending = BirthdayListDomain.Base(sortedList.reversed())

        val actualAscending = source.map(BirthdayListSortMapper.Ascending(idPredicate))
        val actualDescending = source.map(BirthdayListSortMapper.Descending(idPredicate))

        assertEquals(expectedAscending, actualAscending)
        assertEquals(expectedDescending, actualDescending)
    }

    @Test
    fun test_empty_list() {
        val emptyList = BirthdayListDomain.Base()

        assertEquals(emptyList, emptyList.map(BirthdayListSortMapper.Ascending(namePredicate)))
        assertEquals(emptyList, emptyList.map(BirthdayListSortMapper.Ascending(idPredicate)))

        assertEquals(emptyList, emptyList.map(BirthdayListSortMapper.Descending(namePredicate)))
        assertEquals(emptyList, emptyList.map(BirthdayListSortMapper.Descending(idPredicate)))
    }


    private class TestNamePredicate : BirthdayDomain.Mapper<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) = name
    }

    private class TestIdPredicate : BirthdayDomain.Mapper<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) = id
    }
}