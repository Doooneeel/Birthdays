package ru.daniilglazkov.birthdays.ui.core.text.filter

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * @author Danil Glazkov on 07.02.2023, 14:39
 */
class TextFilterChainTest {

    @Test
    fun test_filter() {
        val chain = TextFilterChain(TestTextFilter("1,"), TestTextFilter(" 2"))

        assertEquals("Call order: 1, 2", chain.filter("Call order: "))
    }


    private class TestTextFilter(private val name: String) : TextFilter {
        override fun filter(text: String): String = text + name
    }
}