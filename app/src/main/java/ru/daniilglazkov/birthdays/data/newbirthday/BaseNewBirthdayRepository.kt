package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.data.main.Repository
import ru.daniilglazkov.birthdays.data.newbirthday.cache.NewBirthdayCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayRepository

/**
 * @author Danil Glazkov on 28.08.2022, 19:36
 */
class BaseNewBirthdayRepository(
    cacheDataSource: NewBirthdayCacheDataSource,
    private val dataToDomain: NewBirthdayData.Mapper<NewBirthdayDomain>,
    private val domainToData: NewBirthdayDomainToDataMapper
) : Repository.Abstract<NewBirthdayCacheDataSource, NewBirthdayData, NewBirthdayDomain>(
    cacheDataSource,
) , NewBirthdayRepository
{
    override fun toData(domain: NewBirthdayDomain) = domain.map(domainToData)
    override fun toDomain(data: NewBirthdayData) = data.map(dataToDomain)
}



