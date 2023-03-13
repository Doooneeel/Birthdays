package ru.daniilglazkov.birthdays.domain.birthdaylist

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayCheckMapper
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

class BirthdayListCountMapperTest {

    @Test
    fun test_base() {
        val date = LocalDate.MAX
        val predicate = TestPredicate()

        val mapper = BirthdayListCountMapper.Base(predicate)

        val expected = 3
        val actual = mapper.map(listOf(
            BirthdayDomain.Base(1, "", date),
            BirthdayDomain.Base(2, "", date),
            BirthdayDomain.Base(12, "", date),
            BirthdayDomain.Base(11, "", date),
            BirthdayDomain.Base(4, "", date),
        ))

        assertEquals(expected, actual)
        assertEquals(5, predicate.callCount)
    }

    @Test
    fun test_count_without_headers() {
        val mapper = BirthdayListCountMapper.CountWithoutHeaders()

        val birthdayList1 = listOf<BirthdayDomain>(
            BirthdayDomain.Base(0, "", LocalDate.MIN),
            BirthdayDomain.Base(1, "", LocalDate.MAX),
            BirthdayDomain.Header(-1, ""),
            BirthdayDomain.Header(-2, ""),
            BirthdayDomain.Base(2, "", LocalDate.MIN),
            BirthdayDomain.Base(3, "", LocalDate.MAX),
            BirthdayDomain.Header(-2, ""),
        )
        val birthdayList2 = listOf<BirthdayDomain>()

        val birthdayList3 = listOf<BirthdayDomain>(
            BirthdayDomain.Header(-1, ""),
            BirthdayDomain.Header(-2, ""),
            BirthdayDomain.Header(-2, ""),
        )
        val birthdayList4 = listOf<BirthdayDomain>(
            BirthdayDomain.Base(0, "", LocalDate.MIN),
            BirthdayDomain.Base(1, "", LocalDate.MAX),
            BirthdayDomain.Header(-1, ""),
            BirthdayDomain.Header(-2, ""),
        )
        val birthdayList5 = listOf<BirthdayDomain>(
            BirthdayDomain.Base(0, "", LocalDate.MIN),
            BirthdayDomain.Base(1, "", LocalDate.MAX),
            BirthdayDomain.Base(2, "", LocalDate.MAX),
        )

        assertEquals(4, mapper.map(birthdayList1))
        assertEquals(0, mapper.map(birthdayList2))
        assertEquals(0, mapper.map(birthdayList3))
        assertEquals(2, mapper.map(birthdayList4))
        assertEquals(3, mapper.map(birthdayList5))
    }


    private class TestPredicate : BirthdayCheckMapper {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Boolean {
            ++callCount
            return id < 10
        }
    }
}