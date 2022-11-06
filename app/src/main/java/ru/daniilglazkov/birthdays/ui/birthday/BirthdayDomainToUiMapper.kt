package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.date.DaysToEventTextFormat
import ru.daniilglazkov.birthdays.ui.date.YearTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:35
 */
interface BirthdayDomainToUiMapper : BirthdayDomain.Mapper<BirthdayUi> {

    abstract class Abstract(
        private val dateTextFormat: DateTextFormat,
        private val turnsYearsOldTextFormat: YearTextFormat,
        private val leftDaysToBirthdayTextFormat: DaysToEventTextFormat,
        private val turnsYearsOldDateDifference: DateDifference,
        private val daysToBirthdayDateDifference: DateDifference,
        private val provideString: ProvideString,
        private val nextEvent: NextEvent
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {

            val nextBirthday: LocalDate = nextEvent.nextEvent(date)
            val turnsYearsOld: Int = turnsYearsOldDateDifference.difference(date)
            val daysToBirthday: Int = daysToBirthdayDateDifference.difference(date)

            return BirthdayUi.Base(
                name = name,
                birthdate = dateTextFormat.format(date),
                birthday = dateTextFormat.format(nextBirthday),
                turnsYearsOld = turnsYearsOldTextFormat.format(turnsYearsOld),
                daysLeft = Pair(
                    provideString.quantityString(R.plurals.left, daysToBirthday),
                    leftDaysToBirthdayTextFormat.format(daysToBirthday)
                )
            )
        }
    }

    class Base(nextEvent: NextEvent, provideString: ProvideString, now: LocalDate) : Abstract(
        DateTextFormat.Full(),
        YearTextFormat.Base(provideString),
        DaysToEventTextFormat.OnlyNumbers(provideString),
        DateDifference.TurnsYearsOld(now),
        DateDifference.NextEventInDays(nextEvent, now),
        provideString,
        nextEvent
    )

}