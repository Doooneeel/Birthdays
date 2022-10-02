package ru.daniilglazkov.birthdays.data.newbirthday.cache

import ru.daniilglazkov.birthdays.core.HandleException
import ru.daniilglazkov.birthdays.data.core.cache.CacheDataSource
import ru.daniilglazkov.birthdays.data.main.ProvideNewBirthdayAccess
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayAccess
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayEntity

/**
 * @author Danil Glazkov on 08.09.2022, 04:13
 */
interface NewBirthdayCacheDataSource : CacheDataSource<NewBirthdayData> {

    class Base(
        provideAccess: ProvideNewBirthdayAccess,
        private val dataToDatabaseModel: NewBirthdayData.Mapper<NewBirthdayEntity>,
        handleException: HandleException
    ) : CacheDataSource.AbstractDatabase<NewBirthdayData, NewBirthdayEntity, NewBirthdayAccess>(
        provideAccess.newBirthdayAccess(),
        handleException
    ) , NewBirthdayCacheDataSource {
        override fun dataToEntity(data: NewBirthdayData): NewBirthdayEntity =
            data.map(dataToDatabaseModel)

        override fun getDataFromDatabase(): NewBirthdayEntity? = dataAccess.newBirthday()
    }
}