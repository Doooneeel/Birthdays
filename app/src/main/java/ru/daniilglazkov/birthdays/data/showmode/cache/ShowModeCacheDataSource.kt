package ru.daniilglazkov.birthdays.data.showmode.cache

import ru.daniilglazkov.birthdays.data.showmode.ShowModeData
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 08.09.2022, 04:20
 */
interface ShowModeCacheDataSource {

    fun showMode(): ShowModeData
    fun saveToCache(showMode: ShowModeData)


    class Base(
        private val showModeDao: ShowModeDao,
        private val mapperToCache: ShowModeDataToCacheMapper,
    ) : ShowModeCacheDataSource {

        override fun saveToCache(showMode: ShowModeData) {
            showModeDao.insert(showMode.map(mapperToCache))
        }

        override fun showMode(): ShowModeData {
            val showMode = showModeDao.showMode() ?: throw EmptyCacheException()
            return ShowModeData.Base(showMode.sort, showMode.reverse, showMode.group)
        }
    }
}