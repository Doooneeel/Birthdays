package ru.daniilglazkov.birthdays.ui.main

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.daniilglazkov.birthdays.ui.BaseUiTest
import ru.daniilglazkov.birthdays.ui.birthdaylist.BirthdayListScreen
import ru.daniilglazkov.birthdays.ui.newbirthday.NewBirthdayScreen

/**
 * @author Danil Glazkov on 06.02.2023, 8:58
 */
class SheetViewModelTest : BaseUiTest() {

    private val navigation = TestNavigation()
    private val sheetCommunication = TestSheetCommunication()

    private val viewModel = object : BaseSheetViewModel.Abstract(sheetCommunication, navigation) { }

    @Test
    fun test_navigation() {
        val birthdayListScreen = BirthdayListScreen()
        val newBirthdayScreen = NewBirthdayScreen()

        viewModel.navigate(birthdayListScreen)

        assertEquals(1, navigation.mapCalledList.size)
        assertEquals(birthdayListScreen, navigation.mapCalledList[0])


        viewModel.navigate(newBirthdayScreen)

        assertEquals(2, navigation.mapCalledList.size)
        assertEquals(newBirthdayScreen, navigation.mapCalledList[1])
    }

    @Test
    fun test_navigate_back() {
        viewModel.navigateBack()

        assertEquals(1, sheetCommunication.mapCalledList.size)
    }
}