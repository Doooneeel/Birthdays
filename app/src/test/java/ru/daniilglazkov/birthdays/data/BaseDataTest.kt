package ru.daniilglazkov.birthdays.data

import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao
import ru.daniilglazkov.birthdays.data.core.cache.PreferencesDataStore
import java.util.*

/**
 * @author Danil Glazkov on 27.02.2023, 9:20
 */
abstract class BaseDataTest : TestCore {

    protected abstract class TestAbstractDao<T> : BaseDao<T> {
        var data: T? = null
        val insertCalledList = mutableListOf<T>()

        override fun insert(data: T) {
            insertCalledList.add(data)
            this.data = data
        }
    }

    protected class TestDataStore<T>(
        val data: Hashtable<String, T> = Hashtable(),
    ) : PreferencesDataStore.Mutable<T> {

        val readCalledList = mutableListOf<Pair<String, T>>()
        val saveCalledList = mutableListOf<Pair<String, T>>()

        override fun save(key: String, value: T) {
            data.put(key, value)
            saveCalledList.add(Pair(key, value))
        }

        override fun read(key: String, defaultValue: T): T {
            readCalledList.add(Pair(key, defaultValue))
            return data.get(key) ?: defaultValue
        }
    }
}