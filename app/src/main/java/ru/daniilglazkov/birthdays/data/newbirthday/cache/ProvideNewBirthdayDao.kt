package ru.daniilglazkov.birthdays.data.newbirthday.cache

/**
 * @author Danil Glazkov on 09.10.2022, 20:06
 */
interface ProvideNewBirthdayDao {
    fun provideNewBirthdayDao(): NewBirthdayDao
}