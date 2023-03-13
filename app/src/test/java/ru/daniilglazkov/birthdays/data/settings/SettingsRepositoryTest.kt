package ru.daniilglazkov.birthdays.data.settings

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.data.settings.cache.SettingsCacheDataSource
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortMode
import ru.daniilglazkov.birthdays.domain.birthdaylist.transform.sort.SortModeList

class SettingsRepositoryTest : BaseDataTest() {

    private lateinit var dataSource: TestDataSource
    private lateinit var repository: BaseSettingsRepository

    @Before
    fun setUp() {
        dataSource = TestDataSource()
        repository = BaseSettingsRepository(
            dataSource,
            SettingsDomainToDataMapper.Base(),
            SettingsDataToDomainMapper.Base(SortModeList.Base())
        )
    }

    @Test
    fun test_save() = runBlocking {
        val sortMode = SortMode.DATE
        repository.save(SettingsDomain.Base(sortMode, reverse = false, group = true))

        val expected = SettingsData.Base(sortMode.id(), reverse = false, group = true)

        assertEquals(1, dataSource.saveCalledList.size)
        assertEquals(expected, dataSource.saveCalledList[0])
    }

    @Test
    fun test_read() = runBlocking {
        val sortMode = SortMode.MONTH
        dataSource.data = SettingsData.Base(sortMode.id(), reverse = false, group = false)

        val expected = SettingsDomain.Base(sortMode, reverse = false, group = false)
        val actual: SettingsDomain = repository.read()

        assertEquals(expected, actual)
        assertEquals(1, dataSource.settingsCallCount)
    }

    @Test
    fun test_read_empty_cache() {
        assertThrowInBlock(EmptyCacheException::class.java) {
            repository.read()
        }
        assertEquals(1, dataSource.settingsCallCount)
    }


    private class TestDataSource : SettingsCacheDataSource {
        var data: SettingsData? = null
        var settingsCallCount = 0
        val saveCalledList = mutableListOf<SettingsData>()

        override suspend fun settings(): SettingsData {
            ++settingsCallCount
            return data ?: throw EmptyCacheException()
        }

        override suspend fun saveToCache(settings: SettingsData) {
            saveCalledList.add(settings)
        }
    }
}