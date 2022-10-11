package ru.daniilglazkov.birthdays.data.birthdays.cache

import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import ru.daniilglazkov.birthdays.data.core.Delete
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException

/**
 * @author Danil Glazkov on 08.09.2022, 03:52
 */
interface BirthdayListCacheDataSource : Delete {

    fun addBirthday(birthday: BirthdayData)
    fun birthdays(): List<BirthdayData>
    fun find(id: Int): BirthdayData


    class Base(
        private val birthdaysDao: BirthdaysDao,
        private val mapperToCache: BirthdayDataToCacheMapper,
    ) : BirthdayListCacheDataSource {

        override fun birthdays(): List<BirthdayData> {
            val birthdayList: List<BirthdayCache> = birthdaysDao.allBirthdays()
            return birthdayList.map { BirthdayData.Base(it.id, it.name, it.date) }
        }

        override fun addBirthday(birthday: BirthdayData) {
            birthdaysDao.insert(birthday.map(mapperToCache))
        }

        override fun find(id: Int): BirthdayData {
            val found = birthdaysDao.find(id) ?: throw NotFoundException("ID: $id")
            return BirthdayData.Base(found.id, found.name, found.date)
        }

        override fun deleteById(id: Int) = birthdaysDao.delete(id)
    }
}