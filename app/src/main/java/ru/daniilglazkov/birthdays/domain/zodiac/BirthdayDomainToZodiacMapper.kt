package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.10.2022, 22:40
 */
interface BirthdayDomainToZodiacMapper : BirthdayDomain.Mapper<ZodiacDomain> {

    class Base(
        private val classification: ZodiacGroupClassification
    ) : BirthdayDomainToZodiacMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ZodiacDomain =
            classification.group(date.dayOfYear)
    }
}