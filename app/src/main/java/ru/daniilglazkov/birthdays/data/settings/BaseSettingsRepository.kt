package ru.daniilglazkov.birthdays.data.settings

import ru.daniilglazkov.birthdays.data.settings.cache.SettingsCacheDataSource
import ru.daniilglazkov.birthdays.domain.settings.SettingsDomain
import ru.daniilglazkov.birthdays.domain.settings.SettingsRepository

/**
 * @author Danil Glazkov on 04.08.2022, 03:26
 */
class BaseSettingsRepository(
    private val cacheDataSource: SettingsCacheDataSource,
    private val mapperToData: SettingsDomainToDataMapper,
    private val mapperToDomain: SettingsDataToDomainMapper
) : SettingsRepository {

    override suspend fun read(): SettingsDomain = cacheDataSource.settings()
        .map(mapperToDomain)

    override suspend fun save(data: SettingsDomain) =
        cacheDataSource.saveToCache(data.map(mapperToData))
}