package ru.daniilglazkov.birthdays.data.newbirthday.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.daniilglazkov.birthdays.data.newbirthday.NewBirthdayData
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 08.09.2022, 04:13
 */
interface NewBirthdayCacheDataSource  {

    suspend fun saveToCache(birthday: NewBirthdayData)

    suspend fun newBirthday(): NewBirthdayData


    class Base(
        private val dao: NewBirthdayDao,
        private val mapperToCache: NewBirthdayDataToCacheMapper,
    ) : NewBirthdayCacheDataSource {
        private val mutex = Mutex()

        override suspend fun saveToCache(birthday: NewBirthdayData) = mutex.withLock {
            dao.insert(birthday.map(mapperToCache))
        }

        override suspend fun newBirthday(): NewBirthdayData = mutex.withLock {
            val birthday = dao.newBirthday() ?: throw EmptyCacheException()
            return NewBirthdayData.Base(birthday.name, birthday.epochDay)
        }
    }

}