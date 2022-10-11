package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.data.birthdays.cache.*
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdaylist.*

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
    override fun delete(id: Int) = cacheDataSource.deleteById(id)

    override fun find(id: Int): BirthdayDomain = cacheDataSource.find(id)
        .map(mapperToDomain)

    override fun add(data: BirthdayDomain) {
        cacheDataSource.addBirthday(data.map(mapperToData))
    }
}