package ru.daniilglazkov.birthdays.domain.core.text

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Danil Glazkov on 07.02.2023, 3:53
 */
class AddDelimiterTest {

    @Test
    fun test_base() {
        val delimiter = AddDelimiter.Base(" — ")

        assertEquals("first — second", delimiter.add("first", "second"))
        assertEquals("single", delimiter.add("", "single"))
        assertEquals("0", delimiter.add("0", ""))
        assertEquals("1 — 0", delimiter.add("1", "0"))
        assertEquals("1", delimiter.add("1", "1"))
        assertEquals("text", delimiter.add("text", "text"))
        assertEquals("text", delimiter.add("text", " "))
        assertEquals("a", delimiter.add("a", " "))
        assertEquals("a", delimiter.add(" ", "a"))
    }
}