package ru.daniilglazkov.birthdays.domain.zodiac

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import java.time.LocalDate

/**
 * @author Danil Glazkov on 02.11.2022, 09:34
 */
interface BirthdayDomainToChineseZodiacMapper : BirthdayDomain.Mapper<ChineseZodiacDomain> {

    class Base(fetchZodiacs: FetchChineseZodiacDomainList) : BirthdayDomainToChineseZodiacMapper {
        private val zodiacList = fetchZodiacs.chineseZodiacs()

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ChineseZodiacDomain {
            val ordinalNumber = date.year % 12
            return zodiacList.find { it.matches(ordinalNumber) } ?: throw NotFoundException(
                "Unknown chinese zodiac, ordinal number: $ordinalNumber"
            )
        }
    }
}