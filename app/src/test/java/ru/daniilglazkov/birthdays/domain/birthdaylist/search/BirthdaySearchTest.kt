package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.domain.core.text.NormalizeQuery
import java.time.LocalDate

/**
 * @author Danil Glazkov on 06.03.2023, 14:13
 */
class BirthdaySearchTest : TestCore {

    private lateinit var matchesQuery: TestBirthdayMatchesQuery
    private lateinit var normalizeQuery: TestNormalizeQuery

    private lateinit var birthdaySearch: BirthdaySearch

    @Before
    fun setUp() {
        matchesQuery = TestBirthdayMatchesQuery()
        normalizeQuery = TestNormalizeQuery()

        birthdaySearch = BirthdaySearch.Base(matchesQuery, normalizeQuery)
    }


    @Test
    fun test_empty_list() {
        normalizeQuery.result = listOf("stub")

        assertThrowInBlock(NotFoundException::class.java) {
            birthdaySearch.search(BirthdayListDomain.Base(), "query")
        }
        assertEquals(1, normalizeQuery.calledList.size)
        assertEquals("query", normalizeQuery.calledList[0])

        assertEquals(0, matchesQuery.callCount)
    }

    @Test
    fun test_not_found() {
        val date = LocalDate.MAX
        matchesQuery.result = false
        normalizeQuery.result = listOf("a", "b", "c")

        val birthdays = BirthdayListDomain.Base(
            BirthdayDomain.Base(1, "a", date),
            BirthdayDomain.Base(2, "b", date),
            BirthdayDomain.Base(3, "c", date),
            BirthdayDomain.Base(4, "h", date),
            BirthdayDomain.Base(5, "g", date),
        )
        assertThrows(NotFoundException::class.java) {
            birthdaySearch.search(birthdays, "query")
        }

        assertEquals("query", normalizeQuery.calledList[0])
        assertEquals(1, normalizeQuery.calledList.size)

        assertEquals(birthdays.asList().size, matchesQuery.callCount)
    }

    @Test
    fun test_success() {
        val date = LocalDate.MAX
        matchesQuery.result = true
        normalizeQuery.result = listOf("a", "b", "c")

        val birthdays = BirthdayListDomain.Base(
            BirthdayDomain.Base(1, "a", date),
            BirthdayDomain.Base(2, "b", date),
            BirthdayDomain.Base(3, "c", date),
            BirthdayDomain.Base(4, "h", date),
            BirthdayDomain.Base(5, "g", date),
        )
        assertEquals(birthdays, birthdaySearch.search(birthdays, "query"))

        assertEquals(1, normalizeQuery.calledList.size)
        assertEquals("query", normalizeQuery.calledList[0])

        assertEquals(birthdays.asList().size, matchesQuery.callCount)
    }


    private class TestBirthdayMatchesQuery : BirthdayMatchesQuery {

        var callCount = 0
        var result: Boolean = false

        override fun matches(birthday: BirthdayDomain, queries: Collection<String>): Boolean {
            ++callCount
            return result
        }
    }

    private class TestNormalizeQuery : NormalizeQuery {

        lateinit var result: List<String>
        val calledList = mutableListOf<CharSequence>()

        override fun normalize(query: CharSequence): List<String> {
            calledList.add(query)
            return result
        }
    }
}