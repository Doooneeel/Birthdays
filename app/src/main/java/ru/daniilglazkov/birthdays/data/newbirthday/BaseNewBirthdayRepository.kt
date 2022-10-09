package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayRepository

/**
 * @author Danil Glazkov on 28.08.2022, 19:36
 */
class BaseNewBirthdayRepository(
    private val cacheDataSource: NewBirthdayCacheDataSource,
    private val mapperToDomain: NewBirthdayDataToDomainMapper,
    private val mapperToData: NewBirthdayDomainToDataMapper
) : NewBirthdayRepository {

    override fun newBirthday(): NewBirthdayDomain = cacheDataSource.newBirthday()
        .map(mapperToDomain)

    override fun saveToCache(newBirthday: NewBirthdayDomain) {
        cacheDataSource.saveToCache(newBirthday.map(mapperToData))
    }
}



