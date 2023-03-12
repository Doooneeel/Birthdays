package ru.daniilglazkov.birthdays.data.birthdaylist

import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayDataToDomainMapper
import ru.daniilglazkov.birthdays.data.birthdaylist.cache.BirthdayListCacheDataSource
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import ru.daniilglazkov.birthdays.domain.core.FirstLaunch

/**
 * @author Danil Glazkov on 10.06.2022, 00:51
 */
class BaseBirthdayListRepository(
    private val cacheDataSource: BirthdayListCacheDataSource,
    private val mapperToDomain: BirthdayDataToDomainMapper,
    private val mapperToData: BirthdayDomainToDataMapper,
    private val firstLaunch: FirstLaunch
) : BirthdayListRepository {

    override suspend fun birthdays(): BirthdayListDomain = BirthdayListDomain.Base(
        cacheDataSource.birthdays().map { birthdayData: BirthdayData ->
            birthdayData.map(mapperToDomain)
        }
    )

    override suspend fun delete(id: Int) = cacheDataSource.delete(id)

    override suspend fun find(id: Int): BirthdayDomain = cacheDataSource.find(id)
        .map(mapperToDomain)

    override suspend fun add(data: BirthdayDomain) = cacheDataSource.addBirthday(
        data.map(mapperToData)
    )

    override fun firstLaunch(): Boolean = firstLaunch.firstLaunch()
}