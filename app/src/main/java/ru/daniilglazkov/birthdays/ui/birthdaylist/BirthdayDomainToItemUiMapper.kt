package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.R
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.datetime.*
import ru.daniilglazkov.birthdays.ui.core.resources.ManageResources
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import ru.daniilglazkov.birthdays.ui.core.text.format.DaysToEventTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 24.10.2022, 00:10
 */
interface BirthdayDomainToItemUiMapper : BirthdayDomain.Mapper<BirthdayItemUi> {

    class Factory(
        nextEvent: CalculateNextEvent,
        eventIsToday: EventIsToday,
        resources: ManageResources,
        now: LocalDate,
        private val next: BirthdayDomainToItemUiMapper = UnknownTypeErrorMessage(resources)
    ) : BirthdayDomainToItemUiMapper {
        private val header: BirthdayDomainToItemUiMapper by lazy { Header() }

        private val base: BirthdayDomainToItemUiMapper by lazy {
            Base(resources = resources,
                daysToBirthdayTextFormat = DaysToEventTextFormat.WithText(resources),
                turnsAge = DateDifference.Years.TurnsYearsOld(now),
                daysToBirthday = DateDifference.Days.NextEvent(nextEvent, now),
                eventIsToday = eventIsToday
            )
        }

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayItemUi {
            val mapper: BirthdayDomainToItemUiMapper = when (type) {
                is BirthdayType.Base -> base
                is BirthdayType.Header -> header
                else -> next
            }
            return mapper.map(id, name, date, type)
        }
    }

    class Base(
        private val resources: ProvideString,
        private val daysToBirthdayTextFormat: DaysToEventTextFormat,
        private val turnsAge: DateDifference.Years,
        private val daysToBirthday: DateDifference.Days,
        private val eventIsToday: EventIsToday,
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayItemUi {

            val turnsAge: Int = turnsAge.difference(date)
            val turnsYearsOld = resources.quantityString(R.plurals.age, turnsAge)

            return if (eventIsToday.isToday(date)) {
                BirthdayItemUi.Today(id, name, turnsYearsOld)
            } else {
                val days: Int = daysToBirthday.difference(date)
                val daysLeft = daysToBirthdayTextFormat.format(days)

                BirthdayItemUi.Base(id, name, turnsYearsOld, daysLeft)
            }
        }
    }

    class Header : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            BirthdayItemUi.Header(id, name)
    }

    private class UnknownTypeErrorMessage(
        private val resources: ProvideString,
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            BirthdayItemUi.Message(resources.string(R.string.error))
    }
}