package ru.daniilglazkov.birthdays.ui.core.text.filter

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Danil Glazkov on 07.02.2023, 14:30
 */
class TextFilterTest {

    @Test
    fun test_filter_trim() {
        val filter = TextFilterTrim()

        assertEquals("a", filter.filter("a"))
        assertEquals("a", filter.filter("  a"))
        assertEquals("a", filter.filter("a   "))
        assertEquals("a", filter.filter("   a   "))
        assertEquals("a  a  a", filter.filter("   a  a  a   "))
        assertEquals("a     a", filter.filter("a     a"))
        assertEquals(".  a  .", filter.filter(".  a  ."))
        assertEquals("a a a a", filter.filter(" a a a a "))
    }

    @Test
    fun test_filter_whitespaces() {
        val filter = TextFilterWhitespaces()

        assertEquals("", filter.filter(""))
        assertEquals(" ", filter.filter("  "))
        assertEquals(" ", filter.filter(" "))
        assertEquals(" a b c ", filter.filter("  a  b  c  "))
        assertEquals(" a ", filter.filter("    a    "))
        assertEquals(" a", filter.filter("    a"))
        assertEquals("a ", filter.filter("a    "))
        assertEquals("a b c", filter.filter("a  b    c"))
        assertEquals(" a b c ", filter.filter("   a      b    c  "))
        assertEquals(" .", filter.filter("     ."))
        assertEquals(". ", filter.filter(".     "))
    }
}