package ru.daniilglazkov.birthdays.data.core

import org.junit.Assert.*
import org.junit.Test
import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore

/**
 * @author Danil Glazkov on 27.02.2023, 8:36
 */
class BaseFirstLaunchTest {

    @Test
    fun test_first_launch() {
        val dataStore = TestDataStore()
        val firstLaunch = BaseFirstLaunch(dataStore, "key")

        assertEquals(1, dataStore.readCallCount)
        assertEquals(0, dataStore.saveCallCount)

        assertTrue(firstLaunch.firstLaunch())

        assertEquals(1, dataStore.readCallCount)
        assertEquals(1, dataStore.saveCallCount)


        assertFalse(firstLaunch.firstLaunch())

        assertEquals(1, dataStore.readCallCount)
        assertEquals(1, dataStore.saveCallCount)
    }

    private class TestDataStore : PreferencesDataStore.Mutable<Boolean> {
        var readCallCount = 0
        var saveCallCount = 0

        override fun read(key: String, defaultValue: Boolean): Boolean {
            readCallCount++
            return defaultValue
        }

        override fun save(key: String, value: Boolean) {
            saveCallCount++
        }
    }
}