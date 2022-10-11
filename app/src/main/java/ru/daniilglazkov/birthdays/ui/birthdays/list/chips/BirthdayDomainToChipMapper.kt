package ru.daniilglazkov.birthdays.ui.birthdays.list.chips

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.ui.birthdays.ZodiacTextFormat
import java.time.LocalDate

/**
 * @author Danil Glazkov on 05.10.2022, 11:56
 */
interface BirthdayDomainToChipMapper : BirthdayDomain.Mapper<String> {

    class Base(
        private val zodiacFactory: ZodiacTextFormat
    ) : BirthdayDomainToChipMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            return if (type is BirthdayType.Zodiac) {
                zodiacFactory.format(type)
            } else name
        }
    }
}