package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCacheDataSource
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayRepository

/**
 * @author Danil Glazkov on 28.08.2022, 19:36
 */
class BaseNewBirthdayRepository(
    private val cacheDataSource: NewBirthdayCacheDataSource,
    private val mapperToDomain: NewBirthdayDataToDomainMapper,
    private val mapperToData: NewBirthdayDomainToDataMapper
) : NewBirthdayRepository {

    override suspend fun read(): NewBirthdayDomain = cacheDataSource.newBirthday()
        .map(mapperToDomain)

    override suspend fun save(data: NewBirthdayDomain) {
        cacheDataSource.saveToCache(data.map(mapperToData))
    }
}



