package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.data.birthdays.cache.BirthdayListCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayListRepository

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
class BaseBirthdayListRepository(
    private val cacheDataSource: BirthdayListCacheDataSource,
    private val dataToDomain: BirthdayData.Mapper<BirthdayDomain>,
    private val domainToData: BirthdayDomainToDataMapper,
) : BirthdayListRepository {
    override fun read(default: BirthdayListDomain): BirthdayListDomain {
        return BirthdayListDomain.Base(cacheDataSource.read().map { it.map(dataToDomain) })
    }
    override fun add(data: BirthdayDomain) = cacheDataSource.add(data.map(domainToData))
    override fun delete(id: Int) = cacheDataSource.delete(id)
    override fun find(id: Int): BirthdayDomain = cacheDataSource.find(id)
        .map(dataToDomain)
}
