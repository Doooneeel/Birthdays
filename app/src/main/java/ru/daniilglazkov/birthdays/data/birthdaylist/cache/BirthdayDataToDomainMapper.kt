package ru.daniilglazkov.birthdays.data.birthdaylist.cache

import ru.daniilglazkov.birthdays.data.birthdaylist.BirthdayData
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 09.10.2022, 20:02
 */
interface BirthdayDataToDomainMapper : BirthdayData.Mapper<BirthdayDomain> {

    class Base : BirthdayDataToDomainMapper {
        override fun map(id: Int, name: String, epochDay: Long): BirthdayDomain =
            BirthdayDomain.Base(id, name, LocalDate.ofEpochDay(epochDay))
    }
}