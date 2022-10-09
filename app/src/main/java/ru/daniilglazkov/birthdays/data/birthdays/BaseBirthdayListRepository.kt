package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.data.birthdays.cache.BirthdayDataToDomainMapper
import ru.daniilglazkov.birthdays.data.birthdays.cache.BirthdayListCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthdays.*

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
class BaseBirthdayListRepository(
    private val cacheDataSource: BirthdayListCacheDataSource,
    private val mapperToDomain: BirthdayDataToDomainMapper,
    private val mapperToData: BirthdayDomainToDataMapper,
) : BirthdayListRepository {

    override fun birthdays(): BirthdayListDomain = BirthdayListDomain.Base(
        cacheDataSource.birthdays().map { birthdayData: BirthdayData ->
            birthdayData.map(mapperToDomain)
        }
    )
    override fun delete(id: Int) = cacheDataSource.delete(id)

    override fun find(id: Int): BirthdayDomain = cacheDataSource.find(id)
        .map(mapperToDomain)

    override fun add(data: BirthdayDomain) {
        cacheDataSource.addBirthday(data.map(mapperToData))
    }
}