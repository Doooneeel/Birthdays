package ru.daniilglazkov.birthdays.ui.birthdaylist.chips

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.DeterminePosition
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 14.02.2023, 15:54
 */
class ChipUiTest {

    @Test
    fun test_apply() {
        val view = AbstractView.Text.Test()
        val chipUi = ChipUi.Base(10, "a")
        chipUi.apply(view)

        assertEquals(1, view.calledList.size)
        assertEquals("a", view.calledList[0])
    }

    @Test
    fun test_position() {
        val calculatePosition = TestDeterminePosition(100)
        val chipUi = ChipUi.Base(4, "b")

        assertEquals(100, chipUi.position(calculatePosition))
        assertEquals(1, calculatePosition.calledList.size)
        assertEquals(4, calculatePosition.calledList[0])
    }

    private class TestDeterminePosition(var result: Int) : DeterminePosition {
        val calledList = mutableListOf<Int>()

        override fun position(id: Int): Int {
            calledList.add(id)
            return result
        }
    }
}