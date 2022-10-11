package ru.daniilglazkov.birthdays.domain.newbirthday

/**
 * @author Danil Glazkov on 28.08.2022, 19:29
 */
interface NewBirthdayRepository {
    fun newBirthday(): NewBirthdayDomain
    fun saveToCache(newBirthday: NewBirthdayDomain)
}