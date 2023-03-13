package ru.daniilglazkov.birthdays.ui.newbirthday.about

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 13.02.2023, 21:18
 */
class AboutBirthdateUiTest {

    @Test
    fun test_apply() {
        val turnsYearsOldView = AbstractView.Text.Test()
        val daysToBirthday = AbstractView.Text.Test()

        val aboutBirthday = AboutBirthdateUi.Base("turns years old", "days to birthday")

        aboutBirthday.apply(turnsYearsOldView, daysToBirthday)

        assertEquals(1, turnsYearsOldView.calledList.size)
        assertEquals("turns years old", turnsYearsOldView.calledList[0])

        assertEquals(1, daysToBirthday.calledList.size)
        assertEquals("days to birthday", daysToBirthday.calledList[0])
    }
}