package ru.daniilglazkov.birthdays.data.showmode.cache

import ru.daniilglazkov.birthdays.core.HandleException
import ru.daniilglazkov.birthdays.data.core.cache.CacheDataSource
import ru.daniilglazkov.birthdays.data.main.ProvideShowModeAccess
import ru.daniilglazkov.birthdays.data.showmode.ShowModeAccess
import ru.daniilglazkov.birthdays.data.showmode.ShowModeData
import ru.daniilglazkov.birthdays.data.showmode.ShowModeEntity

/**
 * @author Danil Glazkov on 08.09.2022, 04:20
 */
interface ShowModeCacheDataSource : CacheDataSource<ShowModeData> {

    class Base(
        provideAccess: ProvideShowModeAccess,
        private val dataToDatabaseModel: ShowModeData.Mapper<ShowModeEntity>,
        handleException: HandleException
    ) : CacheDataSource.AbstractDatabase<ShowModeData, ShowModeEntity, ShowModeAccess>(
        provideAccess.showModeAccess(),
        handleException
    ) , ShowModeCacheDataSource {
        override fun dataToEntity(data: ShowModeData) = data.map(dataToDatabaseModel)
        override fun getDataFromDatabase(): ShowModeEntity? = dataAccess.showMode()
    }
}