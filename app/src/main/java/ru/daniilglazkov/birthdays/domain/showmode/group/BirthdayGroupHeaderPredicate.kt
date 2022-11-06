package ru.daniilglazkov.birthdays.domain.showmode.group

import ru.daniilglazkov.birthdays.domain.birthday.BirthdayDomain
import ru.daniilglazkov.birthdays.domain.birthday.BirthdayType
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacGroupClassification
import ru.daniilglazkov.birthdays.domain.date.*
import ru.daniilglazkov.birthdays.domain.zodiac.ZodiacDomain
import java.time.LocalDate

/**
 * @author Danil Glazkov on 17.09.2022, 03:48
 */
interface BirthdayGroupHeaderPredicate<T> : BirthdayDomain.Mapper<T> {

    class MonthAndYear(private val nextEvent: NextEvent) : BirthdayGroupHeaderPredicate<String> {
        private val dateFormat = DateTextFormat.Month()

        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            return "${ dateFormat.format(date) } ${ nextEvent.nextEvent(date).year }"
        }
    }

    class Date(private val dateFormat: DateTextFormat) : BirthdayGroupHeaderPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType) =
            dateFormat.format(date)
    }

    class FirstChar : BirthdayGroupHeaderPredicate<Char> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Char =
            name.first()
    }

    class Range(private val range: DateDifference) : BirthdayGroupHeaderPredicate<Int> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): Int =
            range.difference(date)
    }

    class Zodiac(
        private val classification: ZodiacGroupClassification,
        private val toHeaderMapper: ZodiacDomain.Mapper<String>,
    ) : BirthdayGroupHeaderPredicate<String> {
        override fun map(id: Int, name: String, date: LocalDate, type: BirthdayType): String {
            val zodiac: ZodiacDomain = classification.group(date.dayOfYear)
            return zodiac.map(toHeaderMapper)
        }
    }
}