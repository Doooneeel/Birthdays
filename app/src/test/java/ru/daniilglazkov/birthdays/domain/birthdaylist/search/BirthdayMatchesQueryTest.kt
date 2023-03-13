package ru.daniilglazkov.birthdays.domain.birthdaylist.search

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

class BirthdayMatchesQueryTest {

    private val domainToQueryMapper = TestDomainToQueryMapper()

    @Test
    fun test_incomplete_matches() {
        val matchesQuery = BirthdayMatchesQuery.IncompleteMatch(domainToQueryMapper)

        domainToQueryMapper.result = listOf("")

        assertMatchesQuery(matchesQuery, "")
        assertEquals(1, domainToQueryMapper.callCount)

        domainToQueryMapper.result = listOf("a", "abc", "text")

        assertMatchesQuery(matchesQuery, "")
        assertMatchesQuery(matchesQuery, "a")
        assertMatchesQuery(matchesQuery, "abc")
        assertMatchesQuery(matchesQuery, "text")

        assertEquals(5, domainToQueryMapper.callCount)

        assertMatchesQuery(matchesQuery, "abc", "a", "text")
        assertMatchesQuery(matchesQuery, "a", "abc", "text")
        assertMatchesQuery(matchesQuery, "a", "abc")

        assertMatchesQuery(matchesQuery, "ab")
        assertMatchesQuery(matchesQuery, "b")
        assertMatchesQuery(matchesQuery, "bc")
        assertMatchesQuery(matchesQuery, "c")

        assertMatchesQuery(matchesQuery, "t")
        assertMatchesQuery(matchesQuery, "a")
        assertMatchesQuery(matchesQuery, "te")
        assertMatchesQuery(matchesQuery, "tex")
        assertMatchesQuery(matchesQuery, "ext")
        assertMatchesQuery(matchesQuery, "xt")
        assertMatchesQuery(matchesQuery, "ex")
        assertMatchesQuery(matchesQuery, "a", "bc", "te", "ex", "t", "ab")

        assertMatchesQuery(matchesQuery, "a", "a", "a")
        assertMatchesQuery(matchesQuery, "a", "a", "abc")
        assertMatchesQuery(matchesQuery, "a", "a", "text", "text")
        assertMatchesQuery(matchesQuery, "a", "abc", "abc", "text", "text")
    }

    @Test
    fun test_incomplete_not_matches() {
        val matchesQuery = BirthdayMatchesQuery.IncompleteMatch(domainToQueryMapper)

        domainToQueryMapper.result = emptyList()

        assertNotMatchesQuery(matchesQuery)
        assertEquals(1, domainToQueryMapper.callCount)

        domainToQueryMapper.result = listOf("a", "abc", "text")

        assertNotMatchesQuery(matchesQuery, " ")
        assertNotMatchesQuery(matchesQuery, "XXX")
        assertNotMatchesQuery(matchesQuery, "a", "XXX")
        assertNotMatchesQuery(matchesQuery, "a", "abc", "XXX")
        assertNotMatchesQuery(matchesQuery, "a", "abc", "text", "XXX")

        assertEquals(6, domainToQueryMapper.callCount)

        assertNotMatchesQuery(matchesQuery, "y")
        assertNotMatchesQuery(matchesQuery, "z")

        assertNotMatchesQuery(matchesQuery, "aa")
        assertNotMatchesQuery(matchesQuery, "aa")
        assertNotMatchesQuery(matchesQuery, "a", "abcc", "text")
        assertNotMatchesQuery(matchesQuery, "a", "abc", "textt")

        assertNotMatchesQuery(matchesQuery, "a1")
        assertNotMatchesQuery(matchesQuery, "a1", "abc", "text")

        assertNotMatchesQuery(matchesQuery, "a", "abc", "X")
        assertNotMatchesQuery(matchesQuery, "a", "a", "a", "abc", "abc", "X")
        assertNotMatchesQuery(matchesQuery, "a", "a", "a", "abc", "abc", "text", "text", "X")

        assertNotMatchesQuery(matchesQuery, " a ", " abc ", " text ")
        assertNotMatchesQuery(matchesQuery, " a ", " abc ", "text")
        assertNotMatchesQuery(matchesQuery, " a ", "abc", "text")

        assertNotMatchesQuery(matchesQuery, "a", "dc", "te", "ex", "t", "ab", "x")
        assertNotMatchesQuery(matchesQuery, "x", "a", "dc", "te", "ex", "t", "ab")

        assertNotMatchesQuery(matchesQuery, "aabctext")
        assertNotMatchesQuery(matchesQuery, "abct")
        assertNotMatchesQuery(matchesQuery, "abctext")
        assertNotMatchesQuery(matchesQuery, "aabc")
    }

    @Test
    fun test_incomplete_empty_query() {
        domainToQueryMapper.result = emptyList()
        val incompleteMatch = BirthdayMatchesQuery.IncompleteMatch(domainToQueryMapper)

        assertNotMatchesQuery(incompleteMatch, "")
        assertNotMatchesQuery(incompleteMatch, "a", "")
        assertNotMatchesQuery(incompleteMatch, "a", "a")
        assertNotMatchesQuery(incompleteMatch, " ")
        assertNotMatchesQuery(incompleteMatch, " ", "", " ")

        assertEquals(5, domainToQueryMapper.callCount)
    }


    @Test
    fun test_complete_matches() {
        val matchesQuery = BirthdayMatchesQuery.CompleteMatches(domainToQueryMapper)
        domainToQueryMapper.result = listOf("abc", "text", "a")

        assertMatchesQuery(matchesQuery, "abc")
        assertMatchesQuery(matchesQuery, "text")
        assertMatchesQuery(matchesQuery, "abc")

        assertEquals(3, domainToQueryMapper.callCount)

        assertMatchesQuery(matchesQuery, "abc", "a")
        assertMatchesQuery(matchesQuery, "a", "text")

        assertMatchesQuery(matchesQuery, "a", "a", "text")
        assertMatchesQuery(matchesQuery, "text", "abc", "text", "a")

        assertMatchesQuery(matchesQuery, "a", "abc")
        assertMatchesQuery(matchesQuery, "a", "text")
        assertMatchesQuery(matchesQuery, "abc", "text", "a")
        assertMatchesQuery(matchesQuery, "a", "text", "abc")
        assertMatchesQuery(matchesQuery, "a", "a", "a")
        assertMatchesQuery(matchesQuery, "text", "text", "text", "a")
        assertMatchesQuery(matchesQuery, "abc", "a", "a", "a")
    }

    @Test
    fun test_complete_not_matches() {
        val matchesQuery = BirthdayMatchesQuery.CompleteMatches(domainToQueryMapper)
        domainToQueryMapper.result = listOf("abc", "a", "text")

        assertNotMatchesQuery(matchesQuery, "t")
        assertNotMatchesQuery(matchesQuery, "t", "a")
        assertNotMatchesQuery(matchesQuery, "a", "t")

        assertNotMatchesQuery(matchesQuery, " a ")
        assertNotMatchesQuery(matchesQuery, " text ")
        assertNotMatchesQuery(matchesQuery, " abc ")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text ")
        assertNotMatchesQuery(matchesQuery, " abc", "a", "text ")
        assertNotMatchesQuery(matchesQuery, "abc", " a ", "text")

        assertNotMatchesQuery(matchesQuery, "abc", "a", "text", "")
        assertNotMatchesQuery(matchesQuery, "abcd", "abc", "a", "text")
        assertNotMatchesQuery(matchesQuery, "", "abc", "a", "text")
        assertNotMatchesQuery(matchesQuery, "exta", "abc", "a", "text")
        assertNotMatchesQuery(matchesQuery, "texta", "abc", "a", "text")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text", "")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "textt")
        assertNotMatchesQuery(matchesQuery, "abc", "aa", "text")
        assertNotMatchesQuery(matchesQuery, "abcc", "a", "text")
        assertNotMatchesQuery(matchesQuery, " abc", "a")
        assertNotMatchesQuery(matchesQuery, " abc ", "a")
        assertNotMatchesQuery(matchesQuery, " abc ", " a")
        assertNotMatchesQuery(matchesQuery, " abc ", " a ")
        assertNotMatchesQuery(matchesQuery, " abc ", " a ", " text ")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text ")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text", "v")
        assertNotMatchesQuery(matchesQuery, "a", "text", "abct")

        assertNotMatchesQuery(matchesQuery, "")
        assertNotMatchesQuery(matchesQuery, " a ")
        assertNotMatchesQuery(matchesQuery, " a ", "a")
        assertNotMatchesQuery(matchesQuery, " ")
        assertNotMatchesQuery(matchesQuery, " ", "a")
        assertNotMatchesQuery(matchesQuery, " ", "text", "a")
        assertNotMatchesQuery(matchesQuery, "", "text", "a")

        assertNotMatchesQuery(matchesQuery, "abcc")
        assertNotMatchesQuery(matchesQuery, "abca")
        assertNotMatchesQuery(matchesQuery, "abc a")
        assertNotMatchesQuery(matchesQuery, "abc text a")
        assertNotMatchesQuery(matchesQuery, "abctexta")
        assertNotMatchesQuery(matchesQuery, "1abc")
        assertNotMatchesQuery(matchesQuery, "abc1")
        assertNotMatchesQuery(matchesQuery, "abc", "a1")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text1")
        assertNotMatchesQuery(matchesQuery, "abc", "a", "text", "1")
        assertNotMatchesQuery(matchesQuery, "abc!")
        assertNotMatchesQuery(matchesQuery, "abc?")
        assertNotMatchesQuery(matchesQuery, "@", "a")
        assertNotMatchesQuery(matchesQuery, "!", "text")
    }

    @Test
    fun test_complete_empty_query() {
        domainToQueryMapper.result = emptyList()
        val completeMatches = BirthdayMatchesQuery.CompleteMatches(domainToQueryMapper)

        assertNotMatchesQuery(completeMatches, "")
        assertNotMatchesQuery(completeMatches, "", "")
        assertNotMatchesQuery(completeMatches, "", " ")
        assertNotMatchesQuery(completeMatches, " ", " ")
        assertNotMatchesQuery(completeMatches, " ", " ", " ")
        assertNotMatchesQuery(completeMatches, "a", "")
        assertNotMatchesQuery(completeMatches, "a", "a")
        assertNotMatchesQuery(completeMatches, " ")
        assertNotMatchesQuery(completeMatches, " ", "", " ")
    }


    @Test
    fun test_group() {
        val birthday = BirthdayDomain.Mock()
        val emptyQuery = emptyList<String>()

        val t = TestMatchesQuery(true)
        val f = TestMatchesQuery(false)

        assertTrue(BirthdayMatchesQuery.Group(t, t, t).matches(birthday, emptyQuery))
        assertEquals(1, t.callCount)

        assertTrue(BirthdayMatchesQuery.Group(t).matches(birthday, emptyQuery))
        assertEquals(2, t.callCount)

        assertTrue(BirthdayMatchesQuery.Group(f, t).matches(birthday, emptyQuery))
        assertEquals(3, t.callCount)
        assertEquals(1, f.callCount)

        assertTrue(BirthdayMatchesQuery.Group(t, f).matches(birthday, emptyQuery))
        assertEquals(4, t.callCount)
        assertEquals(1, f.callCount)

        assertTrue(BirthdayMatchesQuery.Group(t, f, f).matches(birthday, emptyQuery))
        assertEquals(5, t.callCount)
        assertEquals(1, f.callCount)

        assertTrue(BirthdayMatchesQuery.Group(f, t, t).matches(birthday, emptyQuery))
        assertEquals(6, t.callCount)
        assertEquals(2, f.callCount)

        assertTrue(BirthdayMatchesQuery.Group(f, f, f, f, f, f, t).matches(birthday, emptyQuery))
        assertEquals(7, t.callCount)
        assertEquals(8, f.callCount)

        assertFalse(BirthdayMatchesQuery.Group(f, f, f).matches(birthday, emptyQuery))
        assertEquals(11, f.callCount)

        assertFalse(BirthdayMatchesQuery.Group(f).matches(birthday, emptyQuery))

        assertFalse(BirthdayMatchesQuery.Group().matches(birthday, emptyQuery))

        assertEquals(12, f.callCount)
        assertEquals(7, t.callCount)
    }


    private fun assertMatchesQuery(matchesQuery: BirthdayMatchesQuery, vararg queries: String) {
        assertTrue(matchesQuery.matches(BirthdayDomain.Mock(), listOf(*queries)))
    }

    private fun assertNotMatchesQuery(matchesQuery: BirthdayMatchesQuery, vararg queries: String) {
        assertFalse(matchesQuery.matches(BirthdayDomain.Mock(), listOf(*queries)))
    }


    private class TestDomainToQueryMapper : BirthdayDomainToQueryMapper {

        lateinit var result: List<String>
        var callCount = 0

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): List<String> {
            ++callCount
            return result
        }
    }

    private class TestMatchesQuery(private val result: Boolean) : BirthdayMatchesQuery {

        var callCount = 0

        override fun matches(birthday: BirthdayDomain, queries: Collection<String>): Boolean {
            ++callCount
            return result
        }
    }
}