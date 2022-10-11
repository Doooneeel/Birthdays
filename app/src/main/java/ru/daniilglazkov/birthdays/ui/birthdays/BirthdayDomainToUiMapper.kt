package ru.daniilglazkov.birthdays.ui.birthdays

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:35
 */
interface BirthdayDomainToUiMapper : BirthdayDomain.Mapper<BirthdayUi> {

    class Factory(private val factory: BirthdayDomainToUiMapperFactory) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi =
            factory.create(type).map(id, name, date, type)
    }

    class BaseSheet(
        private val turnsAgeTextFormat: BirthdateTextFormat,
        private val dateTextFormat: BirthdateTextFormat,
        private val daysToBirthdayTextFormat: BirthdateTextFormat,
        //TODO zodiac
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {
            return BirthdayUi.Base(id, name,
                turnsAgeTextFormat.format(date),
                dateTextFormat.format(date),
                daysToBirthdayTextFormat.format(date)
            )
        }
    }

    class TodaySheet(
        private val turnsAgeTextFormat: BirthdateTextFormat,
        private val dateTextFormat: BirthdateTextFormat,
        private val daysToBirthdayTextFormat: BirthdateTextFormat,
        //TODO zodiac
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {
            return BirthdayUi.Today(id, name,
                turnsAgeTextFormat.format(date),
                dateTextFormat.format(date),
                daysToBirthdayTextFormat.format(date)
            )
        }
    }

    class Base(
        private val turnsAgeTextFormat: BirthdateTextFormat,
        private val daysToBirthdayTextFormat: BirthdateTextFormat,
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi =
            BirthdayUi.Base(id, name, turnsAgeTextFormat.format(date), "",
                daysToBirthdayTextFormat.format(date)
            )
    }

    class Header : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi =
            BirthdayUi.Header(name)
    }

    class Today(private val turnsAgeTextFormat: BirthdateTextFormat) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi =
            BirthdayUi.Today(id, name,turnsAgeTextFormat.format(date))
    }
}