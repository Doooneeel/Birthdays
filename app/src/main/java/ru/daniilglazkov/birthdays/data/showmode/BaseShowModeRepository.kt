package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCacheDataSource
import ru.daniilglazkov.birthdays.domain.showmode.ShowModeDomain
import ru.daniilglazkov.birthdays.domain.showmode.ShowModeRepository

/**
 * @author Danil Glazkov on 04.08.2022, 03:26
 */
class BaseShowModeRepository(
    private val cacheDataSource: ShowModeCacheDataSource,
    private val mapperToData: ShowModeDomainToDataMapper,
    private val mapperToDomain: ShowModeDataToDomainMapper
) : ShowModeRepository {

    override fun readShowMode(): ShowModeDomain = cacheDataSource.showMode()
        .map(mapperToDomain)

    override fun saveShowMode(showMode: ShowModeDomain) =
        cacheDataSource.saveToCache(showMode.map(mapperToData))
}