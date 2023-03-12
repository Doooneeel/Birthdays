package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:52
 */
interface NewBirthdayDomainToUiMapper : NewBirthdayDomain.Mapper<NewBirthdayUi> {

    class Base : NewBirthdayDomainToUiMapper {
        override fun map(name: String, date: LocalDate) = NewBirthdayUi.Base(name, date)
    }
}