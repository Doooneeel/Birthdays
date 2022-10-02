package ru.daniilglazkov.birthdays.data.showmode

import ru.daniilglazkov.birthdays.data.main.Repository
import ru.daniilglazkov.birthdays.data.showmode.cache.ShowModeCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.BirthdayListShowModeRepository
import ru.daniilglazkov.birthdays.domain.birthdays.showmode.ShowModeDomain

/**
 * @author Danil Glazkov on 04.08.2022, 03:26
 */
class BaseShowModeRepository(
    cacheDataSource: ShowModeCacheDataSource,
    private val dataToDomain: ShowModeData.Mapper<ShowModeDomain>,
    private val domainToData: ShowModeDomainToDataMapper
) : Repository.Abstract<ShowModeCacheDataSource, ShowModeData, ShowModeDomain>(cacheDataSource),
    BirthdayListShowModeRepository
{
    override fun toDomain(data: ShowModeData) = data.map(dataToDomain)
    override fun toData(domain: ShowModeDomain) = domain.map(domainToData)
}