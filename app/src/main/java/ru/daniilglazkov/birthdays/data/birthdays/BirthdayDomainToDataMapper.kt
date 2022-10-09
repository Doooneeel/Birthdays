package ru.daniilglazkov.birthdays.data.birthdays

import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 01.09.2022, 21:40
 */
interface BirthdayDomainToDataMapper : BirthdayDomain.Mapper<BirthdayData> {

    class Base : BirthdayDomainToDataMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): BirthdayData =
            BirthdayData.Base(id, name, date)
    }
}
