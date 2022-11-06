package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.core.Add
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.08.2022, 17:13
 */
interface NewBirthdayInteractor {

    fun createNewBirthday(birthday: NewBirthdayDomain)
    fun latestBirthday(): NewBirthdayDomain
    fun aboutBirthdate(date: LocalDate): AboutBirthdateDomain
    fun saveToCache(newBirthday: NewBirthdayDomain)


    class Base(
        private val birthdayListRepository: Add<BirthdayDomain>,
        private val newBirthdayDomainCache: NewBirthdayRepository,
        private val handleResponse: HandleNewBirthdayRepositoryResponse,
        private val daysToBirthdayDateDifference: DateDifference,
        private val ageDateDifference: DateDifference,
    ) : NewBirthdayInteractor {
        override fun aboutBirthdate(date: LocalDate) = AboutBirthdateDomain.Base(
            ageDateDifference.difference(date),
            daysToBirthdayDateDifference.difference(date)
        )
        override fun latestBirthday(): NewBirthdayDomain = handleResponse.handle {
            newBirthdayDomainCache.newBirthday()
        }
        override fun createNewBirthday(birthday: NewBirthdayDomain) {
            birthdayListRepository.add(birthday.create())
        }
        override fun saveToCache(newBirthday: NewBirthdayDomain) {
            newBirthdayDomainCache.saveToCache(newBirthday)
        }
    }
}