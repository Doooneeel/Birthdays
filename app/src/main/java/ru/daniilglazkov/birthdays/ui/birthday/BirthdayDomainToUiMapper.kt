package ru.daniilglazkov.birthdays.ui.birthday

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:35
 */
interface BirthdayDomainToUiMapper : BirthdayDomain.Mapper<BirthdayUi> {

    class Base(
        private val turnsAgeTextFormat: BirthdateTextFormat,
        private val dateTextFormat: BirthdateTextFormat,
        private val daysToBirthdayTextFormat: BirthdateTextFormat,
    ) : BirthdayDomainToUiMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayUi {
            return BirthdayUi.Base(name,
                dateTextFormat.format(date),
                turnsAgeTextFormat.format(date),
                daysToBirthdayTextFormat.format(date)
            )
        }
    }
}