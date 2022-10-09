package ru.daniilglazkov.birthdays.data.newbirthday.cache

import ru.daniilglazkov.birthdays.data.newbirthday.*
import ru.daniilglazkov.birthdays.domain.core.exceptions.EmptyCacheException

/**
 * @author Danil Glazkov on 08.09.2022, 04:13
 */
interface NewBirthdayCacheDataSource  {

    fun saveToCache(newBirthday: NewBirthdayData)
    fun newBirthday(): NewBirthdayData


    class Base(
        private val newBirthdayDao: NewBirthdayDao,
        private val mapperToCache: NewBirthdayDataToCacheMapper,
    ) : NewBirthdayCacheDataSource {

        override fun saveToCache(newBirthday: NewBirthdayData) {
            newBirthdayDao.insert(newBirthday.map(mapperToCache))
        }

        override fun newBirthday(): NewBirthdayData {
            val newBirthday = newBirthdayDao.newBirthday() ?: throw EmptyCacheException()
            return NewBirthdayData.Base(newBirthday.name, newBirthday.date)
        }
    }

}