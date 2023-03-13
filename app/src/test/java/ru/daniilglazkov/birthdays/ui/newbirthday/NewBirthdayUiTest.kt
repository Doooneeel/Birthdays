package ru.daniilglazkov.birthdays.ui.newbirthday

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.03.2023, 16:05
 */
class NewBirthdayUiTest {

    @Test
    fun test_apply() {
        val birthday = NewBirthdayUi.Base("name", LocalDate.MIN)

        val nameView = AbstractView.Text.Test()
        val dateView = AbstractView.Date.Test()

        birthday.apply(nameView, dateView)

        assertEquals(1, nameView.calledList.size)
        assertEquals("name", nameView.calledList[0])

        assertEquals(1, dateView.calledList.size)
        assertEquals(LocalDate.MIN, dateView.calledList[0])
    }

}