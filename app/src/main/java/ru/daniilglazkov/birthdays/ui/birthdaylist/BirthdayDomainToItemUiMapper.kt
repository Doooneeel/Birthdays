package ru.daniilglazkov.birthdays.ui.birthdaylist

import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.EventIsToday
import ru.daniilglazkov.birthdays.ui.date.YearTextFormat
import ru.daniilglazkov.birthdays.ui.date.DaysToEventTextFormat
import ru.daniilglazkov.birthdays.ui.core.resources.ProvideString
import java.time.LocalDate

/**
 * @author Danil Glazkov on 24.10.2022, 00:10
 */
interface BirthdayDomainToItemUiMapper : BirthdayDomain.Mapper<BirthdayItemUi> {

    abstract class Abstract(
        private val turnsAgeTextFormat: YearTextFormat,
        private val daysToBirthdayTextFormat: DaysToEventTextFormat,
        private val turnsAgeDateDifference: DateDifference,
        private val daysToBirthdayDateDifference: DateDifference,
        private val eventIsToday: EventIsToday,
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayItemUi {

            val turnsAge = turnsAgeDateDifference.difference(date)
            val turnsAgeTextFormat = turnsAgeTextFormat.format(turnsAge)

            return when (eventIsToday.isToday(date)) {
                true -> BirthdayItemUi.Today(id, name, turnsAgeTextFormat)
                else -> {
                    val daysToBirthday = daysToBirthdayDateDifference.difference(date)
                    val daysToBirthdayTextFormat = daysToBirthdayTextFormat.format(daysToBirthday)

                    BirthdayItemUi.Base(id, name, turnsAgeTextFormat, daysToBirthdayTextFormat)
                }
            }
        }
    }

    class Base(
        provideString: ProvideString,
        eventIsToday: EventIsToday,
        daysToBirthdayDateDifference: DateDifference,
        now: LocalDate
    ) : Abstract(
        YearTextFormat.Base(provideString),
        DaysToEventTextFormat.WithText(provideString),
        DateDifference.TurnsYearsOld(now),
        daysToBirthdayDateDifference,
        eventIsToday
    )

    class Header : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            BirthdayItemUi.Header(id, name)
    }

    class Factory(
        private val factory: BirthdayDomainToItemUiMapperFactory
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            factory.create(type).map(id, name, date, type)
    }
}