package ru.daniilglazkov.birthdays.ui.birthdaylist

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.core.view.AbstractView

/**
 * @author Danil Glazkov on 14.02.2023, 18:43
 */
class BirthdayItemUiTest {

    private lateinit var headerView : AbstractView.Text.Test
    private lateinit var nameView : AbstractView.Text.Test
    private lateinit var turnsYearsOld : AbstractView.Text.Test
    private lateinit var daysToBirthday : AbstractView.Text.Test

    @Before
    fun setUp() {
        headerView = AbstractView.Text.Test()
        nameView = AbstractView.Text.Test()
        turnsYearsOld = AbstractView.Text.Test()
        daysToBirthday = AbstractView.Text.Test()
    }

    @Test
    fun test_base() {
        val birthdayItemUi = BirthdayItemUi.Base(0, "a", "b", "c")

        assertApply(birthdayItemUi, 1, 1, 1)
        assertApplyHeader(birthdayItemUi, 0)
    }

    @Test
    fun test_header() {
        val birthdayItemUi = BirthdayItemUi.Header(0, "h")

        assertApply(birthdayItemUi, 0, 0, 0)
        assertApplyHeader(birthdayItemUi, 1)
    }

    @Test
    fun test_today() {
        val birthdayItemUi = BirthdayItemUi.Today(0, "a", "b")

        assertApply(birthdayItemUi, 1, 1, 0)
        assertApplyHeader(birthdayItemUi, 0)
    }

    @Test
    fun test_message() {
        val birthdayItemUi = BirthdayItemUi.Message("m")

        assertApply(birthdayItemUi, 0, 0, 0)
        assertApplyHeader(birthdayItemUi, 1)
    }

    private fun assertApply(
        birthdayItemUi: BirthdayItemUi,
        nameCallCount: Int,
        turnsYearsOldCallCount: Int,
        daysToBirthdayCallCount: Int,
    ) {
        birthdayItemUi.apply(nameView, turnsYearsOld, daysToBirthday)

        assertEquals(nameCallCount, nameView.calledList.size)
        assertEquals(turnsYearsOldCallCount, turnsYearsOld.calledList.size)
        assertEquals(daysToBirthdayCallCount, daysToBirthday.calledList.size)
    }

    private fun assertApplyHeader(birthdayItemUi: BirthdayItemUi, headerCallCount: Int) {
        birthdayItemUi.applyHeader(headerView)

        assertEquals(headerCallCount, headerView.calledList.size)
        assertEquals(headerCallCount, headerView.calledList.size)
    }

}