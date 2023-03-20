package ru.daniilglazkov.birthdays.domain.newbirthday

import ru.daniilglazkov.birthdays.domain.datetime.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 16.03.2023, 19:13
 */
interface FetchDateOfBirthInfo {

    fun fetchInfo(date: LocalDate): DateOfBirthInfoDomain


    class Base(
        private val turnsYearsOld: DateDifference.Years,
        private val daysToBirthday: DateDifference.Days,
    ) : FetchDateOfBirthInfo {
        override fun fetchInfo(date: LocalDate) = DateOfBirthInfoDomain.Base(
            turnsYearsOld.difference(date),
            daysToBirthday.difference(date)
        )
    }
}