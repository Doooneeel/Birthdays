package ru.daniilglazkov.birthdays.ui.settings

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.settings.SettingsInteractor
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.ui.BaseUiTest

@ExperimentalCoroutinesApi
class SettingsViewModelTest : BaseUiTest() {

    private lateinit var interactor: TestSettingsInteractor
    private lateinit var communication: TestSettingsCommunication
    private lateinit var sheetCommunication: TestSheetCommunication
    private lateinit var handleSettingsRequest: TestHandleSettings
    private lateinit var dispatches: TestDispatchers

    private lateinit var viewModel: SettingsViewModel


    @Before
    fun setUp() {
        interactor = TestSettingsInteractor()
        communication = TestSettingsCommunication()
        sheetCommunication = TestSheetCommunication()
        handleSettingsRequest = TestHandleSettings()
        dispatches = TestDispatchers()

        viewModel = SettingsViewModel.Base(
            interactor,
            handleSettingsRequest,
            communication,
            dispatches,
            sheetCommunication
        )
    }


    @Test
    fun test_fetch() {
        interactor.result = SettingsDomain.Base(SortMode.NAME, reverse = false, group = true)

        viewModel.fetch()

        assertEquals(1, interactor.fetchSettingsCount)
        assertEquals(1, handleSettingsRequest.handleCallCount)
        assertEquals(interactor.result, handleSettingsRequest.result)
    }

    @Test
    fun test_change_sort_mode() {
        interactor.changeSortModeResult = SettingsDomain.Default

        viewModel.changeSortMode(SortMode.ZODIAC)

        assertEquals(1, handleSettingsRequest.handleCallCount)
        assertEquals(SettingsDomain.Default, handleSettingsRequest.result)

        assertEquals(1, interactor.changeSortModeCalledList.size)
        assertEquals(SortMode.ZODIAC, interactor.changeSortModeCalledList[0])
    }

    @Test
    fun test_change_settings() {
        val newSettings = Pair(false, true)
        interactor.changeResult = SettingsDomain.Default

        viewModel.changeSettings(newSettings.first, newSettings.second)

        assertEquals(1, handleSettingsRequest.handleCallCount)
        assertEquals(SettingsDomain.Default, handleSettingsRequest.result)

        assertEquals(1, interactor.changeCalledList.size)
        assertEquals(newSettings, interactor.changeCalledList[0])
    }

    @Test
    fun test_complete() {
        viewModel.complete()

        assertEquals(1, dispatches.launchBackgroundCallCount)
        assertEquals(1, interactor.saveChangesCallCount)
    }


    private class TestSettingsInteractor : SettingsInteractor {

        lateinit var result: SettingsDomain
        lateinit var changeSortModeResult: SettingsDomain
        lateinit var changeResult: SettingsDomain

        var fetchSettingsCount = 0
        var saveChangesCallCount = 0
        var changeSortModeCalledList = mutableListOf<SortMode>()
        var changeCalledList = mutableListOf<Pair<Boolean, Boolean>>()


        override suspend fun saveChanges() { ++saveChangesCallCount }

        override suspend fun changeSortMode(sort: SortMode): SettingsDomain {
            changeSortModeCalledList.add(sort)
            return changeSortModeResult
        }

        override suspend fun change(reverse: Boolean, group: Boolean): SettingsDomain {
            changeCalledList.add(Pair(reverse, group))
            return changeResult
        }

        override suspend fun fetchSettings(): SettingsDomain {
            ++fetchSettingsCount
            return result
        }
    }

    private class TestHandleSettings : TestHandleDomainRequest<SettingsDomain>(),
        HandleSettingsChange

    private class TestSettingsCommunication : TestAbstractCommunication<SettingsUi>(),
        SettingsCommunication
}