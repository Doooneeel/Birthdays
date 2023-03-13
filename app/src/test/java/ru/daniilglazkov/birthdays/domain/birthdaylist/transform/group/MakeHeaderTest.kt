package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.range.RangeTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 31.01.2023, 0:35
 */
class MakeHeaderTest {

    @Test
    fun test_based_on_first() {
        val predicate = TestBirthdayGroupHeaderPredicate()

        val birthdayList1 = listOf<BirthdayDomain>(
            BirthdayDomain.Mock("first"),
            BirthdayDomain.Mock("second"),
            BirthdayDomain.Mock("last"),
        )
        val birthdayList2 = listOf<BirthdayDomain>(
            BirthdayDomain.Mock("first"),
            BirthdayDomain.Mock("last"),
        )
        val birthdayList3 = listOf<BirthdayDomain>(BirthdayDomain.Mock("first"))
        val emptyBirthdayList = emptyList<BirthdayDomain>()

        val makeHeader = MakeHeader.BasedOnFirst(predicate)

        assertEquals(BirthdayDomain.Header(-1, "first"), makeHeader.make(birthdayList1))
        assertEquals(BirthdayDomain.Header(-2, "first"), makeHeader.make(birthdayList2))
        assertEquals(BirthdayDomain.Header(-3, "first"), makeHeader.make(birthdayList3))

        assertEquals(3, predicate.callCount)

        assertEquals(BirthdayDomain.Header(-4, ""), makeHeader.make(emptyBirthdayList))

        assertEquals(3, predicate.callCount)
    }

    @Test
    fun test_edge() {
        val textFormat = TestRangeTextFormat()
        val predicate = TestBirthdayGroupHeaderPredicate()

        val firstList = listOf<BirthdayDomain>(
            BirthdayDomain.Mock("first"),
            BirthdayDomain.Mock("second"),
            BirthdayDomain.Mock("last"),
        )
        val secondList = listOf<BirthdayDomain>(
            BirthdayDomain.Mock("first"),
            BirthdayDomain.Mock("last"),
        )
        val thirdList = listOf<BirthdayDomain>(BirthdayDomain.Mock("first"))
        val emptyList = emptyList<BirthdayDomain>()

        val makeHeader = MakeHeader.Edges(textFormat, predicate)

        assertEquals(BirthdayDomain.Header(-1, "first-last"), makeHeader.make(firstList))
        assertEquals(BirthdayDomain.Header(-2, "first-last"), makeHeader.make(secondList))
        assertEquals(BirthdayDomain.Header(-3, "first-first"), makeHeader.make(thirdList))

        assertEquals(3, textFormat.callCount)
        assertEquals(6, predicate.callCount)

        assertEquals(BirthdayDomain.Header(-4, ""), makeHeader.make(emptyList))

        assertEquals(3, textFormat.callCount)
        assertEquals(6, predicate.callCount)
    }


    private class TestRangeTextFormat : RangeTextFormat {
        var callCount = 0

        override fun <T : Comparable<T>> format(range: ClosedRange<T>): String {
            ++callCount
            return range.start.toString() + "-" + range.endInclusive.toString()
        }
    }

    private class TestBirthdayGroupHeaderPredicate : BirthdayGroupHeaderPredicate {
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            ++callCount
            return name
        }
    }
}

