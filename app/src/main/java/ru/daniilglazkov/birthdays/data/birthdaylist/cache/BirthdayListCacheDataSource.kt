package ru.daniilglazkov.birthdays.data.birthdaylist.cache

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import ru.daniilglazkov.birthdays.data.birthdaylist.BirthdayData
import ru.daniilglazkov.birthdays.domain.core.Delete
import ru.daniilglazkov.birthdays.domain.core.Find
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 08.09.2022, 03:52
 */
interface BirthdayListCacheDataSource : Delete.Suspend, Find.Suspend<BirthdayData> {

    suspend fun addBirthday(birthday: BirthdayData)

    suspend fun birthdays(): List<BirthdayData>


    class Base(
        private val dao: BirthdaysDao,
        private val mapperToCache: BirthdayDataToCacheMapper,
    ) : BirthdayListCacheDataSource {
        private val mutex = Mutex()

        override suspend fun birthdays(): List<BirthdayData> = mutex.withLock {
            val result = dao.allBirthdays()

            return if (result.isEmpty()) throw EmptyCacheException() else result.map { cache ->
                BirthdayData.Base(cache.id, cache.name, cache.epochDay)
            }
        }

        override suspend fun addBirthday(birthday: BirthdayData) = mutex.withLock {
            dao.insert(birthday.map(mapperToCache))
        }

        override suspend fun find(id: Int): BirthdayData = mutex.withLock {
            val found = dao.find(id) ?: throw NotFoundException("ID: $id")
            return BirthdayData.Base(found.id, found.name, found.epochDay)
        }

        override suspend fun delete(id: Int) = mutex.withLock {
            dao.delete(id)
        }
    }
}