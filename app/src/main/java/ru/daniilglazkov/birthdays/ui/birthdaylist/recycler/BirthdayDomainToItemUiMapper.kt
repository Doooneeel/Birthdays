package ru.daniilglazkov.birthdays.ui.birthdaylist.recycler

import ru.daniilglazkov.birthdays.domain.birthday.*
import ru.daniilglazkov.birthdays.domain.date.EventIsToday
import ru.daniilglazkov.birthdays.ui.birthday.BirthdateTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 24.10.2022, 00:10
 */
interface BirthdayDomainToItemUiMapper : BirthdayDomain.Mapper<BirthdayItemUi> {

    class Factory(
        private val factory: BirthdayDomainToItemUiMapperFactory
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            factory.create(type).map(id, name, date, type)
    }

    class Base(
        private val turnsAgeTextFormat: BirthdateTextFormat,
        private val daysToBirthdayTextFormat: BirthdateTextFormat,
        private val eventIsToday: EventIsToday
    ) : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayItemUi {
            return if (eventIsToday.isToday(date)) {
                BirthdayItemUi.Today(id, name, turnsAgeTextFormat.format(date))
            } else {
                BirthdayItemUi.Base(id, name, turnsAgeTextFormat.format(date),
                    daysToBirthdayTextFormat.format(date)
                )
            }
        }
    }

    class Header : BirthdayDomainToItemUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            BirthdayItemUi.Header(id, name)
    }
}