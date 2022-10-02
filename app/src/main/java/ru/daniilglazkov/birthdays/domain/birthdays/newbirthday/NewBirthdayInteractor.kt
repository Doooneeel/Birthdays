package ru.daniilglazkov.birthdays.domain.birthdays.newbirthday

import ru.daniilglazkov.birthdays.core.Add
import ru.daniilglazkov.birthdays.core.Save
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.08.2022, 17:13
 */
interface NewBirthdayInteractor : Save<NewBirthdayDomain> {

    fun create(Birthday: NewBirthdayDomain)
    fun latestBirthday(): NewBirthdayDomain
    fun calculateDaysToBirthday(date: LocalDate): Int
    fun calculateAge(date: LocalDate): Int

    class Base(
        private val baseRepository: Add<BirthdayDomain>,
        private val newBirthdayDomainCache: NewBirthdayRepository,
        private val toDomain: NewBirthdayDomain.Mapper<BirthdayDomain>,
        private val dateDifference: DateDifference,
        private val year: DateDifference,
        private val now: LocalDate
    ) : NewBirthdayInteractor {
        private var birthdayAdded = false
        private val defaultBirthdayDomain = NewBirthdayDomain.Default(now)

        override fun create(Birthday: NewBirthdayDomain) {
            baseRepository.add(Birthday.map(toDomain))
            save(defaultBirthdayDomain)
            birthdayAdded = true
        }
        override fun latestBirthday(): NewBirthdayDomain {
            return newBirthdayDomainCache.read(default = defaultBirthdayDomain)
        }
        override fun calculateDaysToBirthday(date: LocalDate) = dateDifference.difference(now, date)
        override fun calculateAge(date: LocalDate): Int = year.difference(now, date)

        override fun save(data: NewBirthdayDomain) {
            if (birthdayAdded.not()) {
                newBirthdayDomainCache.save(data)
            }
        }
    }
}