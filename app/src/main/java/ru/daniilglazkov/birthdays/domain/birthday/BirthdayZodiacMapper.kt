package ru.daniilglazkov.birthdays.domain.birthday

import ru.daniilglazkov.birthdays.domain.core.exceptions.NotFoundException
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.chinese.ChineseZodiacDomainList
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate

/**
 * @author Danil Glazkov on 23.10.2022, 22:40
 */
interface BirthdayZodiacMapper : BirthdayDomain.Mapper<ZodiacDomain> {

    class Greek(private val zodiacGroups: GreekZodiacGroups) : BirthdayZodiacMapper {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ZodiacDomain =
            zodiacGroups.group(date.dayOfYear)
    }


    class Chinese(chineseZodiacs: ChineseZodiacDomainList) : BirthdayZodiacMapper {

        private val zodiacs: List<ChineseZodiacDomain> by lazy {
            chineseZodiacs.chineseZodiacs()
        }

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): ZodiacDomain {
            val ordinal = date.year % 12

            return zodiacs.find { it.matches(ordinal) } ?: throw NotFoundException(
                "Unknown chinese zodiac, ordinal: $ordinal"
            )
        }
    }
}