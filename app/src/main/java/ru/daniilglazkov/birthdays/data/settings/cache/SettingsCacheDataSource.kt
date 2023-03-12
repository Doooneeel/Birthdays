package ru.daniilglazkov.birthdays.data.settings.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.daniilglazkov.birthdays.data.settings.SettingsData
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 08.09.2022, 04:20
 */
interface SettingsCacheDataSource {

    suspend fun settings(): SettingsData

    suspend fun saveToCache(settings: SettingsData)


    class Base(
        private val dao: SettingsDao,
        private val mapperToCache: SettingsDataToCacheMapper,
    ) : SettingsCacheDataSource {
        private val mutex = Mutex()

        override suspend fun saveToCache(settings: SettingsData) = mutex.withLock {
            dao.insert(settings.map(mapperToCache))
        }
        
        override suspend fun settings(): SettingsData = mutex.withLock {
            val settings: SettingsCache = dao.settings() ?: throw EmptyCacheException()
            
            return SettingsData.Base(settings.sortModeId, settings.reverse, settings.group)
        }
    }
}