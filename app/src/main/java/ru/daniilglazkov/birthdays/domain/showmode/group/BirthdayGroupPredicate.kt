package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.birthdaylist.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.birthdaylist.zodiac.ZodiacTextFormat
import ru.daniilglazkov.birthdays.domain.date.DateDifference
import ru.daniilglazkov.birthdays.domain.date.DateTextFormat
import ru.daniilglazkov.birthdays.domain.date.NextEvent
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.09.2022, 03:48
 */
interface BirthdayGroupPredicate<T> : BirthdayDomain.Mapper<T> {

    class MonthAndYear(private val nextEvent: NextEvent) : BirthdayGroupPredicate<String> {
        private val dateFormat = DateTextFormat.Month()

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            return "${ dateFormat.format(date) } ${ nextEvent.nextEvent(date).year }"
        }
    }

    class Date(private val dateFormat: DateTextFormat) : BirthdayGroupPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            dateFormat.format(date)
    }

    class FirstChar : BirthdayGroupPredicate<Char> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Char =
            name.first()
    }

    class Range(
        private val range: DateDifference,
        private val before: LocalDate,
    ) : BirthdayGroupPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            range.difference(before, date)
    }

    class Zodiac(
        private val classification: ZodiacGroupClassification,
        private val textFormat: ZodiacTextFormat
    ) : BirthdayGroupPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            textFormat.format(classification.group(date.dayOfYear))
    }
}