package ru.daniilglazkov.birthdays.domain.settings

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.domain.core.TestHandleRequest
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode

/**
 * @author Danil Glazkov on 01.02.2023, 0:52
 */
class SettingsInteractorTest {

    private lateinit var repository: TestSettingsRepository
    private lateinit var changeSettings: TestChangeSettings
    private lateinit var handleRequest: HandleRequest

    private lateinit var interactor: SettingsInteractor

    @Before
    fun setUp() {
        repository = TestSettingsRepository()
        changeSettings = TestChangeSettings()
        handleRequest = HandleRequest()

        interactor = SettingsInteractor.Base(repository, handleRequest, changeSettings)
    }

    @Test
    fun test_fetch() = runBlocking {
        repository.data = SettingsDomain.Base(SortMode.ZODIAC, reverse = true, group = true)
        changeSettings.result = SettingsDomain.Base(SortMode.AGE, reverse = false, group = false)
        handleRequest.result = SettingsDomain.Base(SortMode.NAME, reverse = false, group = true)

        assertEquals(changeSettings.result, interactor.fetchSettings())

        assertEquals(1, repository.readCallCount)
        assertEquals(1, handleRequest.calledList.size)
        assertEquals(repository.data, handleRequest.calledList[0])
        assertEquals(1, changeSettings.changeCalledList.size)
        assertEquals(handleRequest.result, changeSettings.changeCalledList[0])
    }

    @Test
    fun test_fetch_empty_cache() = runBlocking {
        repository.data = null

        changeSettings.result = SettingsDomain.Base(SortMode.MONTH, reverse = true, group = false)
        handleRequest.result = SettingsDomain.Base(SortMode.AGE, reverse = false, group = false)

        assertEquals(changeSettings.result, interactor.fetchSettings())

        assertEquals(1, repository.readCallCount)

        assertEquals(1, handleRequest.exceptionList.size)
        assertEquals(EmptyCacheException(), handleRequest.exceptionList[0])
        assertEquals(0, handleRequest.calledList.size)

        assertEquals(1, changeSettings.changeCalledList.size)
        assertEquals(handleRequest.result, changeSettings.changeCalledList[0])
    }

    @Test
    fun test_change_sort_mode() = runBlocking {
        changeSettings.result = SettingsDomain.Base(SortMode.DATE, reverse = false, group = true)

        val expected = changeSettings.result
        val actual = interactor.changeSortMode(SortMode.ZODIAC)

        assertEquals(expected, actual)
        assertEquals(1, changeSettings.changeCalledList.size)
        assertEquals(
            SettingsDomain.Base(SortMode.ZODIAC, reverse = false, group = true),
            changeSettings.changeCalledList[0]
        )
    }

    @Test
    fun test_change() = runBlocking {
        changeSettings.result = SettingsDomain.Base(SortMode.AGE, reverse = true, group = true)

        assertEquals(
            changeSettings.result,
            interactor.change(reverse = false, group = false)
        )
        assertEquals(1, changeSettings.changeCalledList.size)
        assertEquals(
            SettingsDomain.Base(SortMode.AGE, reverse = false, group = false),
            changeSettings.changeCalledList[0]
        )
    }

    @Test
    fun test_save_changes() = runBlocking {
        changeSettings.result = SettingsDomain.Base(SortMode.MONTH, reverse = false, group = true)

        interactor.saveChanges()

        assertEquals(1, changeSettings.changedValueCallCount)

        assertEquals(1, repository.saveCalledList.size)
        assertEquals(changeSettings.result, repository.saveCalledList[0])
    }


    private class TestChangeSettings : ChangeSettings {

        lateinit var result: SettingsDomain
        var changeCalledList = mutableListOf<SettingsDomain>()
        var changedValueCallCount = 0

        override fun changedValue(): SettingsDomain {
            ++changedValueCallCount
            return result
        }

        override suspend fun change(action: (SettingsDomain) -> SettingsDomain): SettingsDomain {
            changeCalledList.add(action.invoke(result))
            return result
        }
    }

    private class HandleRequest : TestHandleRequest<SettingsDomain>(), HandleSettingsDataRequest
}