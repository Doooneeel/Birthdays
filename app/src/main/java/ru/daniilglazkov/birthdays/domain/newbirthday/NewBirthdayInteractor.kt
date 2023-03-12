package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.core.Add
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.08.2022, 17:13
 */
interface NewBirthdayInteractor {

    suspend fun createNewBirthday(birthday: NewBirthdayDomain)

    suspend fun latestBirthday(): NewBirthdayDomain

    suspend fun saveToCache(newBirthday: NewBirthdayDomain)

    fun aboutBirthdate(date: LocalDate): AboutBirthdateDomain


    class Base(
        private val birthdayListRepository: Add.Suspend<BirthdayDomain>,
        private val newBirthdayRepository: NewBirthdayRepository,
        private val handleResponse: HandleNewBirthdayDataRequest,
        private val turnsYearsOld: DateDifference.Years,
        private val daysToBirthday: DateDifference.Days,
    ) : NewBirthdayInteractor {

        override fun aboutBirthdate(date: LocalDate) = AboutBirthdateDomain.Base(
            turnsYearsOld.difference(date),
            daysToBirthday.difference(date)
        )

        override suspend fun latestBirthday(): NewBirthdayDomain = handleResponse.handle {
            newBirthdayRepository.read()
        }

        override suspend fun createNewBirthday(birthday: NewBirthdayDomain) {
            birthdayListRepository.add(birthday.create())
        }

        override suspend fun saveToCache(newBirthday: NewBirthdayDomain) {
            newBirthdayRepository.save(newBirthday)
        }
    }
}