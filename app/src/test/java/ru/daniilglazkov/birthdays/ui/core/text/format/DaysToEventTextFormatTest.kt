package ru.daniilglazkov.birthdays.ui.core.text.format

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.BaseUiTest

/**
 * @author Danil Glazkov on 08.02.2023, 21:38
 */
class DaysToEventTextFormatTest : BaseUiTest() {

    @Test
    fun test_only_numbers() {
        val manageResources = TestManageResources()
        val textFormat = DaysToEventTextFormat.OnlyNumbers(manageResources)

        assertEquals("10", textFormat.format(10))
        assertEquals(1, manageResources.quantityStringCalledList.size)
        assertEquals(10, manageResources.quantityStringCalledList[0])
    }

    @Test
    fun test_with_text() {
        val manageResources = TestManageResources()
        val textFormat = DaysToEventTextFormat.WithText(manageResources)

        assertEquals("2", textFormat.format(2))
        assertEquals(0, manageResources.stringCount)

        assertEquals(1, manageResources.quantityStringCalledList.size)
        assertEquals(2, manageResources.quantityStringCalledList[0])


        manageResources.string = "tomorrow"
        assertEquals("tomorrow", textFormat.format(1))
        assertEquals(1, manageResources.stringCount)

        manageResources.string = "today"
        assertEquals("today", textFormat.format(0))
        assertEquals(2, manageResources.stringCount)

        assertEquals(1, manageResources.quantityStringCalledList.size)
        assertEquals(2, manageResources.quantityStringCalledList[0])
    }
}