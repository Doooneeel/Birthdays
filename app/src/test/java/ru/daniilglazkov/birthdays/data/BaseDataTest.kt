package ru.daniilglazkov.birthdays.data

import ru.daniilglazkov.birthdays.TestCore
import ru.daniilglazkov.birthdays.data.core.cache.BaseDao

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
}