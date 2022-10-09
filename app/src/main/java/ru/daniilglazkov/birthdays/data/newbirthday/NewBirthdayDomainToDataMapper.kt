package ru.daniilglazkov.birthdays.data.newbirthday

import ru.daniilglazkov.birthdays.domain.birthdays.newbirthday.NewBirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 05.09.2022, 17:11
 */
interface NewBirthdayDomainToDataMapper : NewBirthdayDomain.Mapper<NewBirthdayData> {

    class Base : NewBirthdayDomainToDataMapper {
        override fun map(name: String, date: LocalDate): NewBirthdayData =
            NewBirthdayData.Base(name, date)
    }
}
