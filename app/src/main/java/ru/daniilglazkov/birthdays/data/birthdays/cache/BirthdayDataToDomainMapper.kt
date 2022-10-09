package ru.daniilglazkov.birthdays.data.birthdays.cache

import ru.daniilglazkov.birthdays.data.birthdays.BirthdayData
import ru.daniilglazkov.birthdays.domain.birthdays.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 20:02
 */
interface BirthdayDataToDomainMapper : BirthdayData.Mapper<BirthdayDomain> {

    class Base : BirthdayDataToDomainMapper {
        override fun map(id: Int, name: String, date: LocalDate): BirthdayDomain =
            BirthdayDomain.Base(id, name, date)
    }
}