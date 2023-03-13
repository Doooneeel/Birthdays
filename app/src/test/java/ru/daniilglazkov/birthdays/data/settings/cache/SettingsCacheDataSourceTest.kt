package ru.daniilglazkov.birthdays.data.settings.cache

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import ru.daniilglazkov.birthdays.data.BaseDataTest
import ru.daniilglazkov.birthdays.data.settings.SettingsData
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

class SettingsCacheDataSourceTest : BaseDataTest() {

    private lateinit var dataSource: SettingsCacheDataSource
    private lateinit var dao: TestSettingsDao

    @Before
    fun setUp() {
        dao = TestSettingsDao()

        dataSource = SettingsCacheDataSource.Base(
            dao,
            SettingsDataToCacheMapper.Base()
        )
    }


    @Test
    fun test_save_to_cache() = runBlocking {
        dataSource.saveToCache(SettingsData.Base(1, reverse = false, group = true))

        val expected = SettingsCache(1, reverse = false, group = true)

        assertEquals(1, dao.insertCalledList.size)
        assertEquals(expected, dao.insertCalledList[0])
    }

    @Test
    fun test_settings() = runBlocking {
        dao.data = SettingsCache(1, reverse = false, group = true)

        val expected = SettingsData.Base(1, reverse = false, group = true)
        val actual: SettingsData = dataSource.settings()

        assertEquals(expected, actual)
        assertEquals(1, dao.settingsCallCount)
    }

    @Test
    fun test_settings_empty_cache() {
        assertThrowInBlock(EmptyCacheException::class.java) {
            dataSource.settings()
        }

        assertEquals(1, dao.settingsCallCount)
    }

    private class TestSettingsDao : TestAbstractDao<SettingsCache>(), SettingsDao {
        var settingsCallCount = 0

        override fun settings(): SettingsCache? {
            ++settingsCallCount
            return data
        }
    }
}

