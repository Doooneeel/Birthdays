package ru.daniilglazkov.birthdays.domain.birthdaylist.transform.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.zodiac.*
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacDomain
import ru.daniilglazkov.birthdays.domain.zodiac.greek.GreekZodiacGroups
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.09.2022, 03:48
 */
interface BirthdayGroupHeaderPredicate : BirthdayDomain.Mapper<String> {

    class DateFormat(private val dateFormat: DateTextFormat) : BirthdayGroupHeaderPredicate {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            dateFormat.format(date)
    }

    class FirstCharacterOfName: BirthdayGroupHeaderPredicate {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            if (name.isBlank()) "" else name.first().uppercase()
    }

    class Difference(private val difference: DateDifference) : BirthdayGroupHeaderPredicate {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String =
            difference.difference(date).toString()
    }

    class ZodiacPredicate(
        private val greekZodiacGroup: GreekZodiacGroups,
        private val mapperToHeader: ZodiacDomain.Mapper<String>,
    ) : BirthdayGroupHeaderPredicate {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            val zodiac: GreekZodiacDomain = greekZodiacGroup.group(date.dayOfYear)
            return zodiac.map(mapperToHeader)
        }
    }
}