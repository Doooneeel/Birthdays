package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 19:49
 */
interface NewBirthdayDataToDomainMapper :  NewBirthdayData.Mapper<NewBirthdayDomain> {

    class Base : NewBirthdayDataToDomainMapper {
        override fun map(name: String, date: LocalDate): NewBirthdayDomain =
            NewBirthdayDomain.Base(name, date)
    }
}