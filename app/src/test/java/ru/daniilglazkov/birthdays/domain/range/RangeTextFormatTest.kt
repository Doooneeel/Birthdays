package ru.daniilglazkov.birthdays.domain.range

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.text.AddDelimiter

class RangeTextFormatTest {

    @Test
    fun test_format() {
        val textFormat = RangeTextFormat.Base(AddDelimiter.Base(" / "))

        assertEquals("0 / 12", textFormat.format(0..12))
        assertEquals("xyz / abc", textFormat.format("xyz".."abc"))
        assertEquals("a / b", textFormat.format("a".."b"))
        assertEquals("a", textFormat.format("a".."a"))
        assertEquals("A / x", textFormat.format("A".."x"))
        assertEquals("-123 / 123", textFormat.format(-123..123))
    }

}