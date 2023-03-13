package ru.daniilglazkov.birthdays.ui.settings

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 13.02.2023, 21:03
 */
class SettingsUiTest {

    @Test
    fun test_apply_sort_mode() {
        val sortModeView = AbstractView.AbstractTest<SortMode>()

        val settings = SettingsUi.Base(SortMode.NAME, reverse = false, group = true)
        settings.applySortMode(sortModeView)

        assertEquals(1, sortModeView.calledList.size)
        assertEquals(SortMode.NAME, sortModeView.calledList[0])
    }

    @Test
    fun test_apply() {
        val reverse = AbstractView.Check.Test()
        val group = AbstractView.Check.Test()

        val settings = SettingsUi.Base(SortMode.NAME, reverse = false, group = true)

        settings.apply(reverse, group)

        assertEquals(1, reverse.calledList.size)
        assertEquals(false, reverse.calledList[0])

        assertEquals(1, group.calledList.size)
        assertEquals(true, group.calledList[0])
    }
}