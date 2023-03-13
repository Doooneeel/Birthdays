package ru.daniilglazkov.birthdays.domain.core.text

import org.junit.Test
import ru.daniilglazkov.birthdays.TestCore

class NormalizeQueryTest : TestCore {

    private val normalizeQuery = NormalizeQuery.Base()

    private fun assertQueryEquals(expected: List<String>, actual: String) {
        assertCollectionEquals(expected, normalizeQuery.normalize(actual))
    }

    @Test
    fun test_normalize() {
        assertQueryEquals(listOf(), actual = "")
        assertQueryEquals(listOf(), actual = "     ")
        assertQueryEquals(listOf("a"), actual = "  a   ")
        assertQueryEquals(listOf("a"), actual = "a   a")
        assertQueryEquals(listOf("a"), actual = "a a a a")
        assertQueryEquals(listOf("a"), actual = " a a a a ")
        assertQueryEquals(listOf("a"), actual = " A a A a ")
        assertQueryEquals(listOf("aa", "a", "aaa"), actual = "aa a aaa A")
        assertQueryEquals(listOf("1", "12", "122"), actual = "1 12 122 12")
        assertQueryEquals(listOf(".", "..", "..."), actual = ". . .. ... .. .")
        assertQueryEquals(listOf("1-1", "2-2", "3-3", "1-2", "2-3"), actual = "1-1 2-2 3-3 1-2 2-3")
        assertQueryEquals(listOf("!@#\$%^&*()-=_+[]{}<>,./?:\""), actual = "!@#\$%^&*()-=_+[]{}<>,./?:\"")
        assertQueryEquals(listOf("a_a", "y_y", "a", "y"), actual = "A_a Y_y A a Y y")
        assertQueryEquals(listOf("abc"), actual = "abc Abc ABC abC aBc")
        assertQueryEquals(listOf("a"), actual = "a a  a   a    a     a")
        assertQueryEquals(listOf("a", "b", "c", "d", "e", "f"),actual =  "a b  c   d    e     f")
        assertQueryEquals(listOf("a,b", "c,", "d,", "e,", "f"), actual = "a,b  c,   d,    e,     f")
        assertQueryEquals(listOf("aaa", "aa", "a"), actual = "aaa aa a aaa aa a")
        assertQueryEquals(listOf("abcabc", "abc"), actual = "abcabc abc abc")
        assertQueryEquals(listOf("abcabc", "abc"), actual = "AbcAbc aBc abC abc")
        assertQueryEquals(listOf("a", "123", "xx", "x"), actual = "a   123 XX x X")
        assertQueryEquals(listOf("aa"), actual = "AA aa aA aa")
        assertQueryEquals(listOf("aa", "ab"), actual = "AA aa aA aa Ab ab aB AB")
        assertQueryEquals(listOf("aaa", "aaaa", "aa", "a"), actual = "aaa aaaa aa aaa aaaa a")
    }
}