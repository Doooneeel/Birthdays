package ru.daniilglazkov.birthdays.ui.newbirthday

import ru.daniilglazkov.birthdays.domain.newbirthday.NewBirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 23:04
 */
interface NewBirthdayUiToDomainMapper : NewBirthdayUi.Mapper<NewBirthdayDomain> {

    class Base : NewBirthdayUiToDomainMapper {
        override fun map(name: String, date: LocalDate) = NewBirthdayDomain.Base(name, date)
    }
}