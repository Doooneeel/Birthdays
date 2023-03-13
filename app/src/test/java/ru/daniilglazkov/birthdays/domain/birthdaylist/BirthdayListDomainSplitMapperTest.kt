package ru.daniilglazkov.birthdays.domain.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate
import java.time.Month

/**
 * @author Danil Glazkov on 06.03.2023, 13:57
 */
class BirthdayListDomainSplitMapperTest : TestCore {

    @Test
    fun test_month() {
        val predicate = TestMonthPredicate()
        val splitMapper = BirthdayListDomainSplitMapper.Base(predicate)

        val list = listOf(
            BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 3, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
            BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
        )

        val actual = splitMapper.map(list)
        val expected = listOf(
            BirthdayListDomain.Base(
                BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 1, 1)),
            ),
            BirthdayListDomain.Base(
                BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 2, 1)),
            ),
            BirthdayListDomain.Base(
                BirthdayDomain.Base(0, "", LocalDate.of(1, 3, 1)),
                BirthdayDomain.Base(0, "", LocalDate.of(1, 3, 1)),
            ),
        )
        assertEquals(expected, actual)
        assertEquals(list.size, predicate.callCount)
    }

    @Test
    fun test_id() {
        val predicate = TestIdPredicate()
        val splitMapper = BirthdayListDomainSplitMapper.Base(predicate)
        val date = LocalDate.MIN

        val list = listOf(
            BirthdayDomain.Base(4, "W", date),
            BirthdayDomain.Base(1, "a", date),
            BirthdayDomain.Base(1, "b", date),
            BirthdayDomain.Base(2, "c", date),
            BirthdayDomain.Base(3, "d", date),
            BirthdayDomain.Base(2, "e", date),
            BirthdayDomain.Base(1, "f", date),
            BirthdayDomain.Base(1, "g", date),
            BirthdayDomain.Base(3, "w", date),
            BirthdayDomain.Base(2, "x", date),
            BirthdayDomain.Base(2, "y", date),
        )

        val actual = splitMapper.map(list)
        val expected = listOf(
            BirthdayListDomain.Base(
                BirthdayDomain.Base(4, "W", date),
            ),
            BirthdayListDomain.Base(
                BirthdayDomain.Base(1, "a", date),
                BirthdayDomain.Base(1, "b", date),
                BirthdayDomain.Base(1, "f", date),
                BirthdayDomain.Base(1, "g", date),
            ),
            BirthdayListDomain.Base(
                BirthdayDomain.Base(2, "c", date),
                BirthdayDomain.Base(2, "e", date),
                BirthdayDomain.Base(2, "x", date),
                BirthdayDomain.Base(2, "y", date),
            ),
            BirthdayListDomain.Base(
                BirthdayDomain.Base(3, "d", date),
                BirthdayDomain.Base(3, "w", date),
            ),
        )
        assertEquals(expected, actual)
        assertEquals(list.size, predicate.callCount)
    }

    @Test
    fun test_empty_list() {
        val splitMapper = BirthdayListDomainSplitMapper.Base(TestIdPredicate())
        assertCollectionEquals(emptyList<BirthdayDomain>(), splitMapper.map(emptyList()))
    }

    private class TestIdPredicate : BirthdayDomain.Mapper<Int> {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int {
            ++callCount
            return id
        }
    }

    private class TestMonthPredicate : BirthdayDomain.Mapper<Month> {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Month {
            ++callCount
            return date.month
        }
    }
}