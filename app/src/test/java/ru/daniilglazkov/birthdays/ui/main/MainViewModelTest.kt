package ru.daniilglazkov.birthdays.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.service.core.receivers.ReceiverWrapper
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListScreen

/**
 * @author Danil Glazkov on 03.02.2023, 0:28
 */
class MainViewModelTest : BaseUiTest() {

    @Test
    fun test_navigation_at_start() {
        val testNavigation = TestNavigation()
        val workManagerWrapper = TestReceiverWrapper()

        val viewModel = MainViewModel.Base(workManagerWrapper, testNavigation)

        viewModel.init(true)
        assertEquals(1, testNavigation.mapCalledList.size)
        assertEquals(BirthdayListScreen(), testNavigation.mapCalledList[0])
        assertEquals(1, workManagerWrapper.callCount)

        viewModel.init(false)
        assertEquals(1, testNavigation.mapCalledList.size)
        assertEquals(1, workManagerWrapper.callCount)
    }

    private class TestReceiverWrapper : ReceiverWrapper {
        var callCount = 0

        override fun start() { ++callCount }
    }

}
