package ru.daniilglazkov.birthdays.data.birthdaylist.cache

/**
 * @author Danil Glazkov on 09.10.2022, 20:07
 */
interface ProvideBirthdaysDao {
    fun provideBirthdaysDao(): BirthdaysDao
}