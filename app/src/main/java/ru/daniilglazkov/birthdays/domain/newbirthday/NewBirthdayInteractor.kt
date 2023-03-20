package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdaylist.BirthdayListRepository
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.08.2022, 17:13
 */
interface NewBirthdayInteractor {

    suspend fun createNewBirthday(newBirthday: NewBirthdayDomain)

    suspend fun latestBirthday(): NewBirthdayDomain

    suspend fun saveToCache(newBirthday: NewBirthdayDomain)

    fun dateOfBirthInfo(date: LocalDate): DateOfBirthInfoDomain


    class Base(
        private val birthdayListRepository: BirthdayListRepository,
        private val newBirthdayRepository: NewBirthdayRepository,
        private val handleResponse: HandleNewBirthdayDataRequest,
        private val fetchDateOfBirthInfo: FetchDateOfBirthInfo,
    ) : NewBirthdayInteractor {

        override fun dateOfBirthInfo(date: LocalDate): DateOfBirthInfoDomain =
            fetchDateOfBirthInfo.fetchInfo(date)

        override suspend fun latestBirthday(): NewBirthdayDomain = handleResponse.handle {
            newBirthdayRepository.read()
        }

        override suspend fun createNewBirthday(newBirthday: NewBirthdayDomain) {
            birthdayListRepository.add(newBirthday.create(/*auto generation*/ id = -1))
        }

        override suspend fun saveToCache(newBirthday: NewBirthdayDomain) {
            newBirthdayRepository.save(newBirthday)
        }
    }
}