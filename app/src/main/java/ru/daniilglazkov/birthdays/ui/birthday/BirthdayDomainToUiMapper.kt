package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.core.text.format.DaysToEventTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:35
 */
interface BirthdayDomainToUiMapper : BirthdayDomain.Mapper<BirthdayUi> {

    class Base(
        private val dateTextFormat: DateTextFormat,
        private val leftDaysToBirthdayTextFormat: DaysToEventTextFormat,
        private val turnsYearsOld: DateDifference.Years,
        private val daysToBirthday: DateDifference.Days,
        private val calculateNextEvent: CalculateNextEvent,
        private val resources: ProvideString,
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {

            val nextBirthday: LocalDate = calculateNextEvent.nextEvent(date)
            val turnsYearsOld: Int = turnsYearsOld.difference(date)
            val daysToBirthday: Int = daysToBirthday.difference(date)

            val daysLeft = DaysLeft.Base(
                left = resources.quantityString(R.plurals.left, daysToBirthday),
                days = leftDaysToBirthdayTextFormat.format(daysToBirthday)
            )

            return BirthdayUi.Base(
                name = name,
                birthdate = dateTextFormat.format(date),
                birthday = dateTextFormat.format(nextBirthday),
                turnsYearsOld = resources.quantityString(R.plurals.age, turnsYearsOld),
                daysLeft = daysLeft
            )
        }
    }
}